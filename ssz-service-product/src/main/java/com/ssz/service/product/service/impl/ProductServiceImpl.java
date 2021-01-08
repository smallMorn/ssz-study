package com.ssz.service.product.service.impl;

import com.ssz.service.product.mapper.ProductMapper;
import com.ssz.service.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Integer deleteById(Long productId) {
        int num = productMapper.deleteByPrimaryKey(productId);
        return num;
    }
}
