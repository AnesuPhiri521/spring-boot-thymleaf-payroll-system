package com.bancabc.payroll.dto;

import lombok.Data;

@Data
public class NewUserDto {
    private String fname;
    private String lname;
    private String username;
    private String role;
}
