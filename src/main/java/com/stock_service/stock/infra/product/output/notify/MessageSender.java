package com.stock_service.stock.infra.product.output.notify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock_service.stock.app.product.dto.ProductTransaction;
import com.stock_service.stock.domain.product.model.Supply;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendRollbackNotification(ProductTransaction transaction) {
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(transaction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        rabbitTemplate.convertAndSend("purchase_restore_cart", jsonMessage);
    }

    public void sendSaleNotification(ProductTransaction transaction) {
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(transaction);
            System.out.println(jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        rabbitTemplate.convertAndSend("purchase_register_transaction", jsonMessage);
    }

    public void sendSupplyNotification(Supply supply) {
        String jsonMessage = null;
        try {
            jsonMessage = objectMapper.writeValueAsString(supply);
            System.out.println(jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        rabbitTemplate.convertAndSend("supply_add_transc", supply);
    }
}
