package com.bancabc.payroll.service;

import com.bancabc.payroll.config.UserDtls;
import com.bancabc.payroll.dto.NewUserDto;
import com.bancabc.payroll.dto.NewUserResponseDto;
import com.bancabc.payroll.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService{
    List<User> getAllUsers();

    NewUserResponseDto saveUser(NewUserDto user);

    String getUserRole(String userName);
}
