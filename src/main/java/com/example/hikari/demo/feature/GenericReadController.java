package com.example.hikari.demo.feature;

import com.example.hikari.demo.db.PagingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class GenericReadController {


    private PagingService pagingService;

    public GenericReadController(PagingService pagingService) {
        this.pagingService = pagingService;
    }

    @GetMapping("/{tableName}/find/{pkValue}")
    public Object readSingle(@PathVariable("tableName") String tableName,@PathVariable("pkValue") String id){
        return pagingService.listSingle(tableName,id);
    }

    @GetMapping("/{tableName}/page")
    public Object readMultipleWIthPaging(@PathVariable("tableName") String tableName){
        return pagingService.listAllWIthPaging(tableName);
    }

}
