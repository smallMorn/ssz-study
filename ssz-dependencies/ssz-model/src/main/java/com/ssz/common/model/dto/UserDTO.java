package com.ssz.common.model.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserDTO implements Serializable {

    private String userName;

    private String userSex;

    private Integer userAge;

    private Long productId;
}
