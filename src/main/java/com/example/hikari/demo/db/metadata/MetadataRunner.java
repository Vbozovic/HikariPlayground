package com.example.hikari.demo.db.metadata;


import com.example.hikari.demo.db.PGBeanPropertyRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MetadataRunner implements CommandLineRunner {

    @Value("${database.metadata.introspect}")
    private List<String> tables;

    private Map<String,TableMetaData> tableMapping;

    private DataSource mainDataSource;

    public MetadataRunner(DataSource mainDataSource) {
        this.mainDataSource = mainDataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        JdbcTemplate metadataTemplate = new JdbcTemplate(mainDataSource);
        tableMapping = tables.stream()
                .map(tName-> queryTable(metadataTemplate,tName))
                .collect(Collectors.toMap(TableMetaData::getTableName, Function.identity()));
    }

    private TableMetaData queryTable(JdbcTemplate template,String tableName){
        var data = new TableMetaData();
        RowMapper<ColumnMetaData> mapper = new PGBeanPropertyRowMapper<>(ColumnMetaData.class);
        String q = String.format("select column_name, data_type from INFORMATION_SCHEMA.COLUMNS where table_name = \'%s\'",tableName);
        String pkForTableQ = "SELECT c.column_name, c.data_type\n" +
                "FROM information_schema.table_constraints tc\n" +
                "         JOIN information_schema.constraint_column_usage AS ccu USING (constraint_schema, constraint_name)\n" +
                "         JOIN information_schema.columns AS c ON c.table_schema = tc.constraint_schema\n" +
                "    AND tc.table_name = c.table_name AND ccu.column_name = c.column_name\n" +
                String.format("WHERE constraint_type = 'PRIMARY KEY' and tc.table_name = '%s';",tableName);
        List<ColumnMetaData> columns = template.query(q, mapper);
        data.setColumns(columns);
        data.setTableName(tableName);
        System.out.println("PK for "+tableName);
        var res = template.queryForList(pkForTableQ);

        for (ColumnMetaData column:
             columns) {
            for (var map : res){
                if(column.getColumnName().equals(map.get("column_name"))){
                    column.setPk(true);
                    break;
                }
            }

        }

        return data;
    }


    public Map<String, TableMetaData> getTableMapping() {
        return tableMapping;
    }
}
