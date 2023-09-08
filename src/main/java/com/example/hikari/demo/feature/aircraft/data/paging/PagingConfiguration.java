package com.example.hikari.demo.feature.aircraft.data.paging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class PagingConfiguration implements WebMvcConfigurer{

    private final PagingParamsExtractor extractor;

    public PagingConfiguration(PagingParamsExtractor extractor) {
        this.extractor = extractor;
    }

    @Bean
    @Scope(scopeName = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public PagingBean pagingBean(WebRequest webRequest,PagingParamsExtractor paramsExtractor){
        return new PagingBean(webRequest,paramsExtractor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new PagingResolver(extractor));
    }

}
