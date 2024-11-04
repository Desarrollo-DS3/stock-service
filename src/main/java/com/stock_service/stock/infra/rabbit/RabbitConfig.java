//package com.stock_service.stock.infra.rabbit;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
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
//
//    @Bean
//    public DirectExchange purchaseExchange() {
//        return new DirectExchange("purchase.exchange", false, false);
//    }
//
//    @Bean
//    public Binding purchaseBinding() {
//        return BindingBuilder.bind(purchaseQueue()).to(purchaseExchange()).with("purchase.key");
//    }
//
//    @Bean
//    public Binding rollbackBinding() {
//        return BindingBuilder.bind(rollbackQueue()).to(purchaseExchange()).with("rollback.key");
//    }
//}
