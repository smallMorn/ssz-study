package com.ssz.api.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ssz-service-product",contextId = "productDao")
public interface ProductDao {

    @DeleteMapping(value = "/product/deleteById/{productId}")
    int deleteByProductId(@PathVariable("productId") Long productId);
}
