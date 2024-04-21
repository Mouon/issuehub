package com.everysource.everysource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 허용할 엔드포인트를 지정
                .allowedOrigins("*")        // 모든 출처를 허용하도록 설정 (실제 환경에서는 필요에 따라 수정)
                .allowedMethods("GET");     // GET 메서드만 허용하도록 설정 (실제 환경에서는 필요에 따라 수정)
    }
}

