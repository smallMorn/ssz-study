package com.ssz.service.product.service;

import com.ssz.service.product.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ssz
 * @since 2021-01-13
 */
public interface IProductService extends IService<Product> {

    void deleteById(Long productId);
}
