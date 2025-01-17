package com.ssafy.nuguri.service.hobby;

import com.ssafy.nuguri.domain.baseaddress.BaseAddress;
import com.ssafy.nuguri.domain.category.Category;
import com.ssafy.nuguri.domain.deal.Deal;
import com.ssafy.nuguri.domain.deal.DealFavorite;
import com.ssafy.nuguri.domain.hobby.ApproveStatus;
import com.ssafy.nuguri.domain.hobby.Hobby;
import com.ssafy.nuguri.domain.hobby.HobbyFavorite;
import com.ssafy.nuguri.domain.hobby.HobbyHistory;
import com.ssafy.nuguri.domain.member.Member;
import com.ssafy.nuguri.domain.s3.AwsS3;
import com.ssafy.nuguri.dto.hobby.*;
import com.ssafy.nuguri.exception.ex.CustomException;
import com.ssafy.nuguri.repository.baseaddress.BaseaddressRepository;
import com.ssafy.nuguri.repository.category.CategoryRepository;
import com.ssafy.nuguri.repository.hobby.HobbyFavoriteRepository;
import com.ssafy.nuguri.repository.hobby.HobbyHistoryRepository;
import com.ssafy.nuguri.repository.hobby.HobbyRepository;
import com.ssafy.nuguri.repository.hobby.HobbyRepositoryImpl;
import com.ssafy.nuguri.repository.member.MemberRepository;
import com.ssafy.nuguri.service.s3.AwsS3Service;
import com.ssafy.nuguri.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ssafy.nuguri.exception.ex.ErrorCode.*;
import static com.ssafy.nuguri.exception.ex.ErrorCode.DEAL_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyRepository hobbyRepository;
    private final HobbyHistoryRepository hobbyHistoryRepository;
    private final BaseaddressRepository baseaddressRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public List<HobbyHistoryRegionCategoryRequestDto> findLocalCategoryHobbyList(HobbyListRequestDto hobbyListRequestDto){ // 지역과 카테고리로 취미방 찾기
        return hobbyRepository.findByRegionAndCategory(hobbyListRequestDto.getLocalId(),hobbyListRequestDto.getCategoryId());
    }

    @Transactional
    public HobbyDto findHobbyDetail(Long hobbyId){ // 취미방 상세보기
        return hobbyRepository.hobbyDetail(hobbyId);
    }

    @Transactional
    public Long createHobby(HobbyCreateRequestDto hobbyCreateRequestDto, MultipartFile hobbyImage){ // 취미방 생성
        BaseAddress baseAddress = baseaddressRepository.findById(hobbyCreateRequestDto.getLocalId()).orElseThrow(()->new CustomException(BASEADDRESS_NOT_FOUND));
        Category category = categoryRepository.findById(hobbyCreateRequestDto.getCategoryId()).orElseThrow(()->new CustomException(CATEGORY_NOT_FOUND));
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = new Member();
        member.changeMemberId(memberId);

        String hobbyImageUrl;
        if(hobbyImage == null){
            hobbyImageUrl = "";
        }else{
            AwsS3 awsS3 = new AwsS3();
            try {
                awsS3 = awsS3Service.upload(hobbyImage, "hobbyImage");
            }catch (IOException e){
                System.out.println(e);
            }
            hobbyImageUrl = awsS3.getPath();
        }

        Hobby hobbyEntity = Hobby.builder()
                .baseAddress(baseAddress)
                .category(category)
                .member(member)
                .title(hobbyCreateRequestDto.getTitle())
                .content(hobbyCreateRequestDto.getContent())
                .endDate(hobbyCreateRequestDto.getEndDate())
                .meetingPlace(hobbyCreateRequestDto.getMeetingPlace())
                .isClosed(false)
                .curNum(1)
                .maxNum(hobbyCreateRequestDto.getMaxNum())
                .fee(hobbyCreateRequestDto.getFee())
                .rowAgeLimit(hobbyCreateRequestDto.getRowAgeLimit())
                .highAgeLimit(hobbyCreateRequestDto.getHighAgeLimit())
                .sexLimit(hobbyCreateRequestDto.getSexLimit())
                .hobbyImage(hobbyImageUrl)
                .build();

        // hobby를 생성하면서 hobbyHistory도 같이 생성
        Hobby hobby = hobbyRepository.save(hobbyEntity);


        HobbyHistory hobbyHistoryEntity = HobbyHistory.builder()
                .member(member)
                .hobby(hobby)
                .isPromoter(true)
                .approveStatus(ApproveStatus.APPROVE)
                .build();

        hobbyHistoryRepository.save(hobbyHistoryEntity);

        return hobbyEntity.getId();
    }

}
