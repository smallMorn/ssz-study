package com.ssz.service.product.service.impl;

import com.ssz.service.product.mapper.ProductMapper;
import com.ssz.service.product.service.ProductService;
import io.seata.core.context.RootContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Integer deleteById(Long productId) {
        int num = productMapper.deleteByPrimaryKey(productId);
        return num;
    }
}
