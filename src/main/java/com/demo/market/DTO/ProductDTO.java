package com.demo.market.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {

    private String productNo;

    @NotBlank(message = "商品名不可為空")
    private String name;

    @Min(value = 1, message = "價格必須 >= 1")
    private Integer price;

    @Min(value = 0, message = "庫存不可為負數")
    private Integer stock;

    private String description;
}

