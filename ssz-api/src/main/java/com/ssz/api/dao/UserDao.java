package com.ssz.api.dao;

import com.ssz.common.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ssz-service-user",contextId = "userDao")
public interface UserDao {

    @PostMapping(value = "/user/insert")
    Boolean insert(@RequestBody UserDTO dto);
}
