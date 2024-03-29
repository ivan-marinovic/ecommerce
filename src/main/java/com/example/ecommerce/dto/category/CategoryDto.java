package com.example.ecommerce.dto.category;

import javax.validation.constraints.NotNull;

public class CategoryDto {

    private Long categoryId;
    @NotNull
    private String categoryName;
    @NotNull
    private String description;

    public CategoryDto() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
