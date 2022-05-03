package com.ssz.api.dao;

import com.ssz.common.model.dto.ProductDTO;
import com.ssz.common.web.result.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ssz-service-product",contextId = "productDao")
public interface ProductDao {

    @DeleteMapping(value = "/product/deleteById/{productId}")
    ResultInfo deleteByProductId(@PathVariable("productId") String productId);

    @PostMapping(value = "/product/insert")
    ResultInfo insertProduct(@RequestBody ProductDTO productDTO);
}
