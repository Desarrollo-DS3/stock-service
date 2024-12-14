package com.stock_service.stock.infra.category.output;

import com.stock_service.stock.domain.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {
    CategoryEntity toEntity(Category category);

    List<Category> toCategoriesList(List<CategoryEntity> categoryEntities);

    Category toCategory(CategoryEntity entity);
}
