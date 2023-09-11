package com.example.hikari.demo.paging;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Pagination {


    public static String toLimitAndOffset(PageRequest pageRequest){
        return "LIMIT "+pageRequest.getLimit() + "\nOFFSET "+(pageRequest.getPage()-1)*pageRequest.getLimit()+"\n";
    }

    public static String toGrouping(String additionalColumns,PageRequest pageRequest){
        String groupingColumns = Stream.of(additionalColumns,pageRequest.getSortColumn())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(","));
        return String.format("GROUP BY %s\n",groupingColumns);
    }

    public static String toOrdering(PageRequest pageRequest){
        return String.format("ORDER BY %s %s\n",pageRequest.getSortColumn(),pageRequest.getSortOrder());
    }

}
