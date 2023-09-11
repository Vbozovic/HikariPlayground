package com.example.hikari.demo.db.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnMetaData {

    private String columnName;
    private String dataType;
    private boolean isPk;
}
