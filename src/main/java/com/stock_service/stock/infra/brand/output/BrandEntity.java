package com.stock_service.stock.infra.brand.output;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.stock_service.stock.domain.brand.util.BrandConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = BRAND_TABLE_NAME)
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

}
