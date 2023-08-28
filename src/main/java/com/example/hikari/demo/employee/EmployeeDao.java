package com.example.hikari.demo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDao {

    private Integer enmployeNumber;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private boolean gender;
    private Date hireDate;




}
