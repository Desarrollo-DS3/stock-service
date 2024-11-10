package com.stock_service.stock.infra.category.output;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.stock_service.stock.domain.category.util.CategoryConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CATEGORY_TABLE_NAME)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;
}
