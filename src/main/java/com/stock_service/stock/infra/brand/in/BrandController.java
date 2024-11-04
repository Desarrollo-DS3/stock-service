package com.stock_service.stock.infra.brand.in;

import com.stock_service.stock.app.brand.dto.BrandRequest;
import com.stock_service.stock.app.brand.handler.IBrandHandler;
//import com.stock_service.stock.infra.rabbit.MessageSender;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@Validated
public class BrandController {
    private static final String CREATE_BRAND_URL = "/create";

    private final IBrandHandler brandHandler;

//    @Autowired
//    private MessageSender messageSender;

    public BrandController(IBrandHandler brandHandler) {
        this.brandHandler = brandHandler;
    }

    @PostMapping(CREATE_BRAND_URL)
    ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        //messageSender.sendMessage(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @RabbitListener(queues = "rollback_queue")
//    public void rollbackPurchase() {
//        System.out.println("Rolling back purchase of ");
//    }
}
