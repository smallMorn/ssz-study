package com.ssz.service.product.controller;


import com.ssz.common.web.result.ResultInfo;
import com.ssz.service.product.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ssz
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @DeleteMapping("/deleteById/{productId}")
    public ResultInfo deleteById(@PathVariable Long productId){
        productService.deleteById(productId);
        return ResultInfo.success();
    }
}
