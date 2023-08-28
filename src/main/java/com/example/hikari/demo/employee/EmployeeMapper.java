package com.example.hikari.demo.employee;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper implements RowMapper<EmployeeDao> {
    @Override
    public EmployeeDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeDao employee = new EmployeeDao();
        employee.setEnmployeNumber(rs.getInt("emp_no"));
        employee.setBirthDate(rs.getDate("birth_date"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setHireDate(rs.getDate("hire_date"));
        String gender = rs.getString("gender");
        boolean actualGender = switch (gender){
            case "F" -> true;
            default -> false;
        };
        employee.setGender(actualGender);
        return employee;
    }
}
