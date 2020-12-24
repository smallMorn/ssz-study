package com.ssz.service.product.entity;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {
    /**
     * 主键id
     */
    @Id
    @Column(name = "product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品价格
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;
}