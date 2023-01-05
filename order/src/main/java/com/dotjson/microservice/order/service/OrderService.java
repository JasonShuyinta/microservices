package com.dotjson.microservice.order.service;

import com.dotjson.microservice.order.common.Payment;
import com.dotjson.microservice.order.common.TransactionRequest;
import com.dotjson.microservice.order.common.TransactionResponse;
import com.dotjson.microservice.order.entity.Order;
import com.dotjson.microservice.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest) throws JsonProcessingException {
        String response = "";
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        log.info("OrderService request: {}", new ObjectMapper().writeValueAsString(transactionRequest));
        //rest call
        Payment paymentResponse = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
        log.info("PaymentService response from OrderService Rest call: {}", new ObjectMapper().writeValueAsString(paymentResponse));
        response = paymentResponse.getPaymentStatus().equals("success") ? "Payment processing successful and order placed" : "There is a failure in payment api, order added to cart";
        orderRepository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
    }
}
