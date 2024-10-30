package com.stock_service.stock.domain.brand.usecase;

import com.stock_service.stock.domain.brand.api.IBrandServicePort;
import com.stock_service.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.stock_service.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.stock_service.stock.domain.brand.model.Brand;
import com.stock_service.stock.domain.brand.spi.IBrandPersistencePort;

import static com.stock_service.stock.domain.brand.exception.BrandExceptionMessage.*;
import static com.stock_service.stock.domain.brand.util.BrandConstants.*;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {
        String brandName = brand.getName();
        String brandDescription = brand.getDescription();

        if (brandName == null || brandName.trim().isEmpty())
            throw new BrandNotValidFieldException(EMPTY_NAME);

        if (brandDescription == null || brandDescription.trim().isEmpty())
            throw new BrandNotValidFieldException(EMPTY_DESCRIPTION);

        if (brandName.length() > MAX_NAME_LENGTH)
            throw new BrandNotValidFieldException(TOO_LONG_NAME);

        if (brandDescription.length() > MAX_DESCRIPTION_LENGTH)
            throw new BrandNotValidFieldException(TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(brandPersistencePort.isBrandPresentByName(brandName)))
            throw new BrandAlreadyExistException(ALREADY_EXIST_BRAND);

        brandPersistencePort.createBrand(brand);
    }
}
