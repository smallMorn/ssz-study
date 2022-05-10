package com.ssz.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Date 2022/5/3 20:46
 * Author ssz
 */
@Data
public class ProductDTO implements Serializable {

    private String productId;

    private String productName;
}
