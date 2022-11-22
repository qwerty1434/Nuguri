import { useRouter } from "next/router";
import { useEffect, useState } from "react";

export const useLoading = () => {
  const [pageLoading, setPageLoading] = useState(false);
  const router = useRouter();
  useEffect(() => {
    const handleStart = () => {
      setPageLoading(true);
    };
    const handleComplete = () => {
      setPageLoading(false);
    };

    router.events.on("routeChangeStart", handleStart);
    router.events.on("routeChangeComplete", handleComplete);
    router.events.on("routeChangeError", handleComplete);
  }, [router]);
  return { pageLoading };
};
