package com.stock_service.stock.infra.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue purchaseRestoreCartQueue() {
        return new Queue("purchase_restore_cart", false);
    }

    @Bean
    public Queue purchaseDiscountStockQueue() {
        return new Queue("purchase_discount_stock", false);
    }

    @Bean
    public Queue purchaseRestoreStockQueue() {
        return new Queue("purchase_restore_stock", false);
    }

    @Bean
    public Queue purchaseRegisterTransactionQueue() {
        return new Queue("purchase_register_transaction", false);
    }

    @Bean
    public Queue supplyAddTransactionQueue() {
        return new Queue("supply_add_transc", false);
    }

    @Bean
    public Queue supplyRestoreStockQueue() {
        return new Queue("supply_restore_stock", false);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }
}