package com.anesuphiri521.payroll;


import com.anesuphiri521.payroll.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
@AllArgsConstructor
public class PayrollApplication {

	public static void main(String[] args) {
		new File(Constants.uploadDir).mkdir();
		SpringApplication.run(PayrollApplication.class, args);
	}

}
