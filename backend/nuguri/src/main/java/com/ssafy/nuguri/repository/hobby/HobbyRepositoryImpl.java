package com.ssafy.nuguri.repository.hobby;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.nuguri.domain.hobby.ApproveStatus;
import com.ssafy.nuguri.domain.hobby.Hobby;
import com.ssafy.nuguri.dto.hobby.HobbyDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.nuguri.domain.baseaddress.QBaseAddress.baseAddress;
import static com.ssafy.nuguri.domain.category.QCategory.category;
import static com.ssafy.nuguri.domain.hobby.QHobby.hobby;

public class HobbyRepositoryImpl implements HobbyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public HobbyRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<HobbyDto> findByRegion(Long RegionId) {
        List<HobbyDto> hobbyDtoList = queryFactory.select(Projections.constructor(HobbyDto.class,
                hobby.id,
                baseAddress.id,
                category.id,
                hobby.title,
                hobby.content,
                hobby.endDate,
                hobby.meetingPlace,
                hobby.isClosed,
                hobby.curNum,
                hobby.maxNum,
                hobby.fee,
                hobby.ageLimit,
                hobby.sexLimit,
                hobby.hobbyImage
                ))
                .from(hobby)
                .innerJoin(hobby.baseAddress, baseAddress)
                .innerJoin(hobby.category, category)
                .where(baseAddress.id.eq(RegionId))
                .fetch();
        return hobbyDtoList;
    }

    @Override
    public List<HobbyDto> findByRegionAndCategory(Long RegionId, Long CategoryId) {
        List<HobbyDto> hobbyDtoList = queryFactory.select(Projections.constructor(HobbyDto.class,
                        hobby.id,
                        baseAddress.id,
                        category.id,
                        hobby.title,
                        hobby.content,
                        hobby.endDate,
                        hobby.meetingPlace,
                        hobby.isClosed,
                        hobby.curNum,
                        hobby.maxNum,
                        hobby.fee,
                        hobby.ageLimit,
                        hobby.sexLimit,
                        hobby.hobbyImage
                ))
                .from(hobby)
                .innerJoin(hobby.baseAddress, baseAddress)
                .innerJoin(hobby.category, category)
                .where(
                    baseAddress.id.eq(RegionId)
                    .and(category.id.eq(CategoryId))
                )
                .fetch();
        return hobbyDtoList;
    }

    @Override
    public List<HobbyDto> findMultipleRegionAndCategory(List<Long> RegionIds, List<Long> CategoryIds) {
        return null;
    }

    @Override
    public HobbyDto hobbyDetail(Long hobbyId) {
        HobbyDto hobbyDto = queryFactory.select(Projections.constructor(HobbyDto.class,
                        hobby.id,
                        baseAddress.id,
                        category.id,
                        hobby.title,
                        hobby.content,
                        hobby.endDate,
                        hobby.meetingPlace,
                        hobby.isClosed,
                        hobby.curNum,
                        hobby.maxNum,
                        hobby.fee,
                        hobby.ageLimit,
                        hobby.sexLimit,
                        hobby.hobbyImage
                ))
                .from(hobby)
                .innerJoin(hobby.baseAddress, baseAddress)
                .innerJoin(hobby.category, category)
                .where(
                        hobby.id.eq(hobbyId)
                )
                .fetchOne();
        return hobbyDto;
    }

}