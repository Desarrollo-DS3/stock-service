//package com.stock_service.stock.infra.rabbit;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stock_service.stock.app.brand.dto.BrandRequest;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageSender {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public void sendMessage(BrandRequest request) {
//        String jsonMessage = null;
//        try {
//            jsonMessage = objectMapper.writeValueAsString(request);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        rabbitTemplate.convertAndSend("purchase.exchange", "purchase.key", jsonMessage);
//    }
//}
