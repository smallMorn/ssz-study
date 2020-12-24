package com.ssz.service.product.service.impl;

import com.ssz.service.product.mapper.ProductMapper;
import com.ssz.service.product.service.ProductService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    @GlobalTransactional
    public Integer deleteById(Long productId) {
        int num = productMapper.deleteByPrimaryKey(productId);
        return num;
    }
}
