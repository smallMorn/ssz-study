package com.ssz.service.user.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    /**
     * 0男  1女
     */
    @Column(name = "user_sex")
    private Integer userSex;

    @Column(name = "user_age")
    private Integer userAge;
}