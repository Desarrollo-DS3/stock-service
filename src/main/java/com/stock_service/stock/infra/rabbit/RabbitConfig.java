//package com.stock_service.stock.infra.rabbit;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//
//    @Bean
//    public Queue rollbackQueue() {
//        return new Queue("rollback_queue", false);
//    }
//
//    @Bean Queue purchaseQueue(){
//        return new Queue("purchase_queue", false);
//    }
//}
