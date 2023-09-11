package com.example.hikari.demo.db.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableMetaData {

    private String tableName;
    private List<ColumnMetaData> columns;

    public boolean add(ColumnMetaData columnMetaData) {
        return columns.add(columnMetaData);
    }

}
