package com.example.hikari.demo.feature.aircraft.data.paging;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Pagination {


    public static String toLimitAndOffset(PageRequest pageRequest){
        return "LIMIT "+pageRequest.getLimit() + "\nOFFSET "+(pageRequest.getPage()-1)*pageRequest.getLimit();
    }

}
