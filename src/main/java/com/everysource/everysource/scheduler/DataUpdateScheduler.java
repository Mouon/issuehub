package com.everysource.everysource.scheduler;

import com.everysource.everysource.service.GitHubDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class DataUpdateScheduler {
    @Autowired
    private GitHubDataService gitHubDataService;

    @Scheduled(cron = "0 * * * * ?")
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
