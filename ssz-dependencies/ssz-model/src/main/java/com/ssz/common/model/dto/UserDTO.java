package com.ssz.common.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {

    private String userName;

    private Integer userSex;

    private Integer userAge;

    private Long productId;
}
