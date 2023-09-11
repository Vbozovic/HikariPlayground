package com.example.hikari.demo.db;

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
        if(isObjectOfType("jsonb",rsObj)){
            PGobject obj = (PGobject)rsObj;
            String jsonValue = obj.getValue();
            try {
                return om.readValue(jsonValue, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else if (isObjectOfType("point",rsObj)) {
            PGobject obj = (PGobject)rsObj;
            return obj.getValue();
        }
        return super.getColumnValue(rs, index, pd);
    }

    private boolean isObjectOfType(String type,Object object){
        return object instanceof PGobject && type.equals(((PGobject)object).getType());
    }

    @Override
    protected Object getColumnValue(ResultSet rs, int index, Class<?> paramType) throws SQLException {
        if(paramType.equals(PGBinaryObject.class)){
            PGobject obj = (PGobject) rs.getObject(index);
        }
        return super.getColumnValue(rs, index, paramType);
    }
}
