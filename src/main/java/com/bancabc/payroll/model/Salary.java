package com.bancabc.payroll.model;

import com.bancabc.payroll.utils.Currency;
import com.bancabc.payroll.utils.PaymentState;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private String receiverName;
    private String receiverAccountNumber;
    private String senderName;
    private String senderAccountNumber;
    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;
    @CreationTimestamp
    private Date dateCreated;
    @UpdateTimestamp
    private Date dateApprovedOrRejected;

    public Salary() {
    }

    public Salary(Double amount, String receiverName, String receiverAccountNumber, String senderName, String senderAccountNumber, PaymentState paymentState) {
        this.amount = amount;
        this.receiverName = receiverName;
        this.receiverAccountNumber = receiverAccountNumber;
        this.senderName = senderName;
        this.senderAccountNumber = senderAccountNumber;
        this.paymentState = paymentState;
    }
}
