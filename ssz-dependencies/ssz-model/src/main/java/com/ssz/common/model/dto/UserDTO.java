package com.ssz.common.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;




@Data
@Accessors(chain = true)
public class UserDTO {

    @ApiModelProperty(value = "用户姓名",example = "张三")
    private String userName;

    @ApiModelProperty(value = "用户性别:0男 1女",example = "1")
    private Integer userSex;

    @ApiModelProperty(value = "用户年龄",example = "22")
    private Integer userAge;

    @ApiModelProperty(value = "商品id",example = "1")
    private Long productId;
}
