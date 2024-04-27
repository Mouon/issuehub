//package com.everysource.everysource.launcher;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BatchJobLauncher {
//
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private Job fetchGitHubDataJob;
//
//    public void launchJob(String owner, String repo) throws Exception {
//        JobParameters parameters = new JobParametersBuilder()
//                .addString("owner", owner)
//                .addString("repo", repo)
//                .toJobParameters();
//        jobLauncher.run(fetchGitHubDataJob, parameters);
//    }
//}
//
