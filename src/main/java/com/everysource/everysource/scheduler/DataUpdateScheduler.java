package com.everysource.everysource.scheduler;

import com.everysource.everysource.service.api.GitHubDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DataUpdateScheduler {
    @Autowired
    private GitHubDataService gitHubDataService;

//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 */6 * * ?")
    public void updateGitHubData() {
        log.info("GitHub 데이터 업데이트를 위한 스케줄러가 트리거되었습니다.");
        List<String> repositories = List.of("django/django", "spring-projects/spring-boot", "facebook/react", "flutter/flutter","elastic/elasticsearch","facebook/react-native",
                "android/architecture-samples");
        repositories.forEach(repo -> {
            String[] parts = repo.split("/");
            log.info("저장소 {}의 데이터를 가져오는 중입니다: {}/{}", repo, parts[0], parts[1]);
            gitHubDataService.fetchIssues(parts[0], parts[1]);
            gitHubDataService.fetchReadme(parts[0], parts[1]);
        });
        log.info("GitHub 데이터 업데이트를 완료했습니다.");
    }

}




/**
 *      0: 분 - 0분에 실행
 *      0: 시 - 0시에 실행
 *      *'/'6:모든 시간을 6으로 나눈 나머지가 0인 시간에 실행(즉,0시,6시,12시,18시에 실행)
 *      *:모든 일
 *      *:모든 월
 *      *?:요일에 대한 특정 값 없음
 * */
