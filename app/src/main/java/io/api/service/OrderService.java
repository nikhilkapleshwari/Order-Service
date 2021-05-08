package io.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.api.common.Payment;
import io.api.common.TransactionRequest;
import io.api.common.TransactionResponse;
import io.api.entity.Order;
import io.api.repository.OrderRepository;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    @Lazy
    private RestTemplate restTemplate;
    
    @Value("${microservice.payment-service.endpoints.endpoint.uri:abc}")
    private String ENDPOINT_URL;
    
    @Value("${msg:Hello}")
    private String msg;
    
    public TransactionResponse saveOrder(TransactionRequest request) {
      
      String response="";
      Order order = request.getOrder();
      Payment payment = request.getPayment();
      payment.setOrderId(order.getId());
      payment.setAmount(order.getPrice());
      
      System.out.println("ENDPOINT_URL fetch:"+ENDPOINT_URL);
      System.out.println("msg fetch:"+msg);
      Payment paymentResponse = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
      
      response = (paymentResponse!=null && paymentResponse.getPaymentStatus().equals("success"))?"payment processing success":"failure in payment processing";
      orderRepository.save(order);
      
      return new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
    }
}
