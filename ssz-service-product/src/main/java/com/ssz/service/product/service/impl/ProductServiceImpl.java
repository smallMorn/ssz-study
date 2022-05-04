package com.ssz.service.product.service.impl;

import com.ssz.common.model.dto.ProductDTO;
import com.ssz.service.product.entity.Product;
import com.ssz.service.product.mapper.ProductMapper;
import com.ssz.service.product.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    private final ProductMapper productMapper;

    @Override
    public void deleteById(Long productId) {
        productMapper.deleteById(productId);
    }

    @Override
    public void insert(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        long timeMillis = System.currentTimeMillis();
        productMapper.insert(product);
        log.info("产品服务插入耗时:{}", System.currentTimeMillis() - timeMillis);
    }
}
