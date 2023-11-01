package com.anesuphiri521.payroll.repository;

import com.anesuphiri521.payroll.model.Salary;
import com.anesuphiri521.payroll.utils.Enums.PaymentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByPaymentState(PaymentState paymentState);
    @Query(value = "SELECT sum(amount) FROM BancAbcPayroll.Salary where date(dateCreated)=CURRENT_DATE and currency='ZWL'", nativeQuery = true)
    Double findTodayTotalValueZWL();

    @Query(value = "SELECT sum(amount) FROM BancAbcPayroll.Salary where date(dateCreated)=CURRENT_DATE and currency='USD'", nativeQuery = true)
    Double findTodayTotalValueUSD();

    @Query(value = "SELECT count(amount) FROM BancAbcPayroll.Salary where date(dateCreated)=CURRENT_DATE", nativeQuery = true)
    Integer todayTotal();
}
