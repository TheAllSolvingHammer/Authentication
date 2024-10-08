package com.tinqinacademy.authentication.rest.interceptor;


import com.tinqinacademy.authentication.api.mappings.MappingConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    private final SimpleUserInterceptor simpleUserInterceptor;
    @Autowired
    public WebConfig(SimpleUserInterceptor simpleUserInterceptor) {
        this.simpleUserInterceptor = simpleUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(simpleUserInterceptor).addPathPatterns(
                MappingConstants.demote
                ,MappingConstants.promote
                ,MappingConstants.change,MappingConstants.logout);
    }
}
