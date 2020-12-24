package com.ssz.service.user.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Region {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String province;

    private String city;
}