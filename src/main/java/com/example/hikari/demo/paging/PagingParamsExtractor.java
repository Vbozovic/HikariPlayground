package com.example.hikari.demo.paging;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Component
public class PagingParamsExtractor {

    public static String PAGING_PREFIX = "paging-";
    public static String PAGING_LIMIT = PAGING_PREFIX + "limit";
    public static String PAGING_PAGE = PAGING_PREFIX + "page";
    public static String PAGING_SORT_COLUMN = PAGING_PREFIX + "column";
    public static String PAGING_SORT_ORDER = PAGING_PREFIX + "order";

    private static Integer DEFAULT_LIMIT = 50;
    private static Integer DEFAULT_PAGE = 1;
    private static PageRequest.SortOrder DEFAULT_SORT = PageRequest.SortOrder.DESC;

    public PageRequest extract(WebRequest request){
        PageRequest pr = new PageRequest();

        Integer limit = getLimit(request);
        Integer page = getPage(request);
        String column = getParameter(request,PAGING_SORT_COLUMN);
        String orderString = Optional.ofNullable(getParameter(request,PAGING_SORT_ORDER)).orElse("ASC");

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

    private Integer getLimit(WebRequest webRequest){
        String param = getParameter(webRequest,PAGING_LIMIT);
        if (param == null){
            return DEFAULT_LIMIT;
        }
        Integer limit = Integer.parseInt(getParameter(webRequest,PAGING_LIMIT));
        return limit;
    }

    private Integer getPage(WebRequest webRequest){
        String param = getParameter(webRequest,PAGING_PAGE);
        if (param == null){
            return DEFAULT_PAGE;
        }
        Integer page = Integer.parseInt(getParameter(webRequest,PAGING_PAGE));
        return page;
    }

}
