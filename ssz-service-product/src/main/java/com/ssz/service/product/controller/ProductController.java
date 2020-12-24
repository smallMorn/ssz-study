package com.ssz.service.product.controller;

import com.ssz.service.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    @DeleteMapping(value = "deleteById/{productId}")
    public Integer deleteById(@PathVariable Long productId){
        return productService.deleteById(productId);
    }
}
