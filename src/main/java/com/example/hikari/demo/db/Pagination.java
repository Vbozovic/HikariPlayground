package com.example.hikari.demo.db;

import com.example.hikari.demo.PageRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Pagination {


    public static String toLimitAndOffset(PageRequest pageRequest){
        return "LIMIT "+pageRequest.getLimit() + "\nOFFSET "+(pageRequest.getPage()-1)*pageRequest.getLimit();
    }

}
