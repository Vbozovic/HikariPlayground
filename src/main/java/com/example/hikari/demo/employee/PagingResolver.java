package com.example.hikari.demo.employee;

import com.example.hikari.demo.PageRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;


public class PagingResolver implements HandlerMethodArgumentResolver {

    private static String PAGING_PREFIX = "paging-";
    private static String PAGING_LIMIT = PAGING_PREFIX + "limit";
    private static String PAGING_PAGE = PAGING_PREFIX + "page";
    private static String PAGING_SORT_COLUMN = PAGING_PREFIX + "column";
    private static String PAGING_SORT_ORDER = PAGING_PREFIX + "order";

    private static Integer DEFAULT_LIMIT = 50;
    private static Integer DEFAULT_PAGE = 1;
    private static PageRequest.SortOrder DEFAULT_SORT = PageRequest.SortOrder.DESC;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        PageRequest pr = new PageRequest();

        Integer limit = Optional.ofNullable(Integer.parseInt(getParameter(webRequest,PAGING_LIMIT))).orElse(DEFAULT_LIMIT);
        Integer page = Optional.ofNullable(Integer.parseInt(getParameter(webRequest,PAGING_PAGE))).orElse(DEFAULT_PAGE);
        String column = getParameter(webRequest,PAGING_SORT_COLUMN);
        String orderString = Optional.ofNullable(getParameter(webRequest,PAGING_SORT_ORDER)).orElse("ASC");

        pr.setLimit(limit);
        pr.setPage(page);
        pr.setSortColumn(column);

        PageRequest.SortOrder order = switch (orderString) {
            case "ASC" -> PageRequest.SortOrder.ASC;
            default -> PageRequest.SortOrder.DESC;
        };

        pr.setSortOrder(order);

        return pr;
    }

    private String getParameter(WebRequest webRequest, String name){
        return webRequest.getParameter(name);
    }

}
