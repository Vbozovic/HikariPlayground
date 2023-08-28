package com.example.hikari.demo.employee;

import com.example.hikari.demo.PageRequest;
import com.example.hikari.demo.db.Pagination;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private JdbcTemplate jdbcTemplate;

    public EmployeeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<EmployeeDao> getEmployees(PageRequest pageRequest){
        String q = "SELECT * from employees\n" +
                "order by employees."+pageRequest.getSortColumn()+" "+pageRequest.getSortOrder() + ",employees.emp_no\n"+ Pagination.toLimitAndOffset(pageRequest);
        List<EmployeeDao> employees = jdbcTemplate.query(q,new EmployeeMapper());
        return employees;
    }

}
