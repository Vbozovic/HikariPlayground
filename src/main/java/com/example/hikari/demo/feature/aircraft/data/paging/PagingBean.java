package com.example.hikari.demo.feature.aircraft.data.paging;

import org.springframework.web.context.request.WebRequest;

import javax.annotation.PostConstruct;

public class PagingBean {

    private WebRequest request;
    private PagingParamsExtractor paramsExtractor;

    private PageRequest pageRequest;

    public PagingBean(WebRequest request, PagingParamsExtractor paramsExtractor) {
        this.request = request;
        this.paramsExtractor = paramsExtractor;
    }

    @PostConstruct
    void readRequest(){
        pageRequest = paramsExtractor.extract(request);
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }
}
