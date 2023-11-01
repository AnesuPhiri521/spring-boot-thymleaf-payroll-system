package com.anesuphiri521.payroll;

import com.anesuphiri521.payroll.model.User;
import com.anesuphiri521.payroll.service.UserService;
import com.anesuphiri521.payroll.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class PayrollApplicationTests {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void getUsersTest(){
		when(userRepository.findAll()).thenReturn(Stream.of(new User("Anesu", "Phiri", "aphiri", true, new Date())).collect(Collectors.toList()));
		assertEquals(1, userService.getAllUsers().size());
	}

}
