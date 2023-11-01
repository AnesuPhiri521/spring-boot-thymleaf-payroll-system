package com.anesuphiri521.payroll;

import com.anesuphiri521.payroll.model.Salary;
import com.anesuphiri521.payroll.utils.Enums.PaymentState;
import com.anesuphiri521.payroll.repository.SalaryRepository;
import com.anesuphiri521.payroll.service.SalariesService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
@RunWith(SpringRunner.class)
class SalariesTest {

    @Autowired
    private SalariesService salariesService;

    @MockBean
    private SalaryRepository salaryRepository;

    @Test
    public void captureIndividualSalary(){
        Salary newSalary = new Salary(1000.1, "123456789", "987654321", "BancABC", "90000988", PaymentState.APPROVED);
        when(salariesService.saveSalary(newSalary)).thenReturn(newSalary);
        assertEquals(newSalary, salariesService.saveSalary(newSalary));
    }

    @Test
    public void getPendingApprovalSalariesTest(){
        when(salaryRepository.findByPaymentState(PaymentState.PENDING)).thenReturn(Stream.of(new Salary()).collect(Collectors.toList()));
        assertEquals(1, salariesService.getPendingSalaries().size());
    }

    @Test
    public void getSalaryAllSalaries(){
        when(salaryRepository.findAll()).thenReturn(Stream.of(new Salary(1000.1, "123456789", "987654321", "BancABC", "90000988", PaymentState.APPROVED)).collect(Collectors.toList()));
        assertEquals(1, salariesService.getAllSalaries().size());
    }
}

