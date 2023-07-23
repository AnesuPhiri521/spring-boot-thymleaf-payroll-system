package com.bancabc.payroll.service;

import com.bancabc.payroll.model.Salary;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SalariesService {
    List<Salary> getAllSalaries();



    Salary saveSalary(Salary salary);

    String saveBatchUploadExcel(String fileName) throws IOException;

    Optional<Salary> findPaymentById(Long id);

    void updateSalaryApprove(Long id);
    void updateSalaryReject(Long id);

    List<Salary> getPendingSalaries();

    Double todayTotalValueUSD();
    Double todayTotalValueZWL();
    Integer todayTotal();

    String saveBatchUploadCSV(String fileName) throws IOException;
}
