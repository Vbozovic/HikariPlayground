package com.example.hikari.demo.db;

import com.example.hikari.demo.db.metadata.ColumnMetaData;
import com.example.hikari.demo.db.metadata.MetadataRunner;
import com.example.hikari.demo.paging.Pagination;
import com.example.hikari.demo.paging.PagingBean;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PagingService implements ApplicationContextAware {

    /**
     * Maps table names to jdbc templates
     */
    private Map<String,JdbcTemplate> templateMappings;
    private MetadataRunner metadata;
    private PagingBean page;

    private ApplicationContext context;

    public PagingService(MetadataRunner metadata, PagingBean page) {
        this.metadata = metadata;
        this.page = page;
    }

    public Object listSingle(String tableName,String id){
        var tableMetaData = metadata.getTableMapping().get(tableName);
        var pkColumn = findPk(tableMetaData.getColumns());

        JdbcTemplate template = templateMappings.get(tableName);
        String query = String.format("select * from %s\n",tableName);
        String where = where(pkColumn,id);

        return template.queryForMap(query+where);
    }

    private String where(ColumnMetaData column,String id){
        String where = "where %s = %s\n";
        String columnSyntax = switch (column.getDataType()){
            case "character","varcharacter" -> String.format("\'%s\'",id);
            case "integer" -> String.format("%s",id);
            default -> throw new RuntimeException("Where mapping ex "+column.getDataType());
        };
        return String.format(where,column.getColumnName(),columnSyntax);
    }

    private ColumnMetaData findPk(List<ColumnMetaData> columns){
        return columns
                .stream()
                .filter(ColumnMetaData::isPk)
                .findFirst()
                .get();
    }

    public Object listAllWIthPaging(String tableName){
        JdbcTemplate template = templateMappings.get(tableName);
        var tableMetaData = metadata.getTableMapping().get(tableName);

        String select = String.format("select * from %s\n",tableName);

        var primaryKeys = tableMetaData.getColumns()
                .stream().filter(ColumnMetaData::isPk)
                .map(ColumnMetaData::getColumnName)
                .collect(Collectors.joining(","));

        select = select + Pagination.toGrouping(primaryKeys,page.getPageRequest());
        select = select + Pagination.toOrdering(page.getPageRequest());
        select = select + Pagination.toLimitAndOffset(page.getPageRequest());

        return template.queryForList(select);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onStarted(){
        this.templateMappings = this.context.getBeansOfType(JdbcTemplate.class);
    }

}
