package com.anesuphiri521.payroll.service;

import com.anesuphiri521.payroll.dto.NewUserDto;
import com.anesuphiri521.payroll.dto.NewUserResponseDto;
import com.anesuphiri521.payroll.model.User;

import java.util.List;

public interface UserService{
    List<User> getAllUsers();

    NewUserResponseDto saveUser(NewUserDto user);

    String getUserRole(String userName);
}
