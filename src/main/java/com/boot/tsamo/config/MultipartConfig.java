//package com.boot.tsamo.config;
//
//import com.boot.tsamo.service.FileAttributeService;
//import jakarta.servlet.MultipartConfigElement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.util.unit.DataSize;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.support.StandardServletMultipartResolver;
//
//@Configuration
//public class MultipartConfig implements ApplicationRunner {
//
//    @Autowired
//    private FileAttributeService fileAttributeService;
//
//    @Autowired
//    private ConfigurableEnvironment environment;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Long maxRequestSize = fileAttributeService.getMaxRequestSize(1L); // 예시로 ID 1로 설정
//        if (maxRequestSize != null) {
//            // 환경 변수 설정
//            environment.getSystemProperties().put("spring.servlet.multipart.maxRequestSize", maxRequestSize + "MB");
//        } else {
//            throw new IllegalStateException("Max request size property is not set");
//        }
//    }
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 위에서 설정한 시스템 속성 값 사용
//        String maxRequestSizeStr = System.getProperty("spring.servlet.multipart.maxRequestSize");
//        if (maxRequestSizeStr != null) {
//            factory.setMaxRequestSize(DataSize.parse(maxRequestSizeStr));
//        } else {
//            throw new IllegalStateException("Max request size property is not set");
//        }
//        return factory.createMultipartConfig();
//    }
//}
