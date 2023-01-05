package com.dotjson.microservice.payment.service;

import com.dotjson.microservice.payment.entity.Payment;
import com.dotjson.microservice.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        log.info("PaymentService request: {}", new ObjectMapper().writeValueAsString(payment));
        return paymentRepository.save(payment);
    }


    public String paymentProcessing() {
        //api should be 3rd party payment gateway (paypal...)
        return new Random().nextBoolean()? "success" : "false";
    }

    public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
        Payment payment = paymentRepository.findByOrderId(orderId);
        log.info("PaymentService findPaymentHistoryByOrderId: {}", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }
}
