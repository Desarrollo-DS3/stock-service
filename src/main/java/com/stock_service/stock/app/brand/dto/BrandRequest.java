package com.stock_service.stock.app.brand.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.stock_service.stock.domain.brand.exception.BrandExceptionMessage.*;
import static com.stock_service.stock.domain.brand.util.BrandConstants.MAX_DESCRIPTION_LENGTH;
import static com.stock_service.stock.domain.brand.util.BrandConstants.MAX_NAME_LENGTH;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    @NotNull(message = EMPTY_NAME)
    @NotEmpty(message = EMPTY_NAME)
    @Size(max = MAX_NAME_LENGTH, message = TOO_LONG_NAME)
    private String name;

    @NotNull(message = EMPTY_DESCRIPTION)
    @NotEmpty(message = EMPTY_DESCRIPTION)
    @Size(max = MAX_DESCRIPTION_LENGTH, message = TOO_LONG_DESCRIPTION)
    private String description;
}
