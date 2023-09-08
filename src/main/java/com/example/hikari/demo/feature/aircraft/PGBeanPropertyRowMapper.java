package com.example.hikari.demo.feature.aircraft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGBinaryObject;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PGBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    private ObjectMapper om;

    public PGBeanPropertyRowMapper(Class<T> mappedClass) {
        super(mappedClass);
        om = new ObjectMapper();
    }

    @Override
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        Object rsObj = rs.getObject(index);
        if(rsObj instanceof PGobject && "jsonb".equals(((PGobject)rsObj).getType())){
            PGobject obj = (PGobject)rs.getObject(index);
            String jsonValue = obj.getValue();
            try {
                return om.readValue(jsonValue, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return super.getColumnValue(rs, index, pd);
    }

    @Override
    protected Object getColumnValue(ResultSet rs, int index, Class<?> paramType) throws SQLException {
        if(paramType.equals(PGBinaryObject.class)){
            PGobject obj = (PGobject) rs.getObject(index);
        }
        return super.getColumnValue(rs, index, paramType);
    }
}
