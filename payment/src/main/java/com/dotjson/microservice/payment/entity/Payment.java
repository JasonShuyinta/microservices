package com.dotjson.microservice.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PAYMENT_TB")
public class Payment {

    @Id
    @GeneratedValue
    private int paymentId;

    private String paymentStatus;

    private String transactionId;

    private int orderId;

    private double amount;
}
