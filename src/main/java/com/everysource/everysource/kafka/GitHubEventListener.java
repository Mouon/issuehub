//package com.everysource.everysource.kafka;
//
//import com.everysource.everysource.service.GitHubDataService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//@Slf4j
//public class GitHubEventListener {
//    private final GitHubDataService gitHubDataService;
//    private final Set<String> repositories;
//
//    @Autowired
//    public GitHubEventListener(GitHubDataService gitHubDataService) {
//        this.gitHubDataService = gitHubDataService;
//        this.repositories = Set.of("django/django", "spring-projects/spring-boot", "facebook/react", "flutter/flutter","elastic/elasticsearch","facebook/react-native", "android/architecture-samples");
//    }
//
//    @KafkaListener(topics = "github-issues", groupId = "github-event-listener")
//    public void handleGitHubIssues(String message) {
//        log.info("[GitHubEventListener] Received GitHub Issue Event: {}", message);
//        processEvent(message, "issue");
//    }
//
//    @KafkaListener(topics = "github-readme", groupId = "github-event-listener")
//    public void handleGitHubReadme(String message) {
//        log.info("[GitHubEventListener] Received GitHub Readme Event: {}", message);
//        processEvent(message, "readme");
//    }
//
//    private void processEvent(String message, String eventType) {
//        String[] parts = message.split("/");
//        if (parts.length < 2) {
//            log.error("[GitHubEventListener] Invalid message format: {}", message);
//            return;
//        }
//
//        String repoKey = parts[0] + "/" + parts[1];
//        if (repositories.contains(repoKey)) {
//            try {
//                log.info("[GitHubEventListener] Processing GitHub {} Event for repository: {}", eventType, repoKey);
//                if ("issue".equals(eventType)) {
//                    gitHubDataService.fetchIssues(parts[0], parts[1]);
//                } else if ("readme".equals(eventType)) {
//                    gitHubDataService.fetchReadme(parts[0], parts[1]);
//                }
//                log.info("[GitHubEventListener] GitHub {} Event processed successfully for repository: {}", eventType, repoKey);
//            } catch (Exception e) {
//                log.error("[GitHubEventListener] Error processing GitHub {} Event for repository {}: {}", eventType, repoKey, e.getMessage());
//            }
//        }
//    }
//}
