package com.ssz.service.product.service.impl;

import com.ssz.service.product.entity.Product;
import com.ssz.service.product.mapper.ProductMapper;
import com.ssz.service.product.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ssz
 * @since 2021-01-13
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    private final ProductMapper productMapper;

    @Override
    public void deleteById(Long productId) {
        productMapper.deleteById(productId);
    }
}
