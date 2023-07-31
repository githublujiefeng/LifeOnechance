package com.learn.web;

import com.learn.web.Interceptor.TestInterceptorHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfigurer  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTestInterceptorHandle());

    }

    @Bean
    public TestInterceptorHandle getTestInterceptorHandle(){
        return new TestInterceptorHandle();
    }
}
