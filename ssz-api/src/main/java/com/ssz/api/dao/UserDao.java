package com.ssz.api.dao;

import com.ssz.common.model.dto.UserDTO;
import com.ssz.common.model.dto.UserQueryDTO;
import com.ssz.common.model.result.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ssz-service-user",contextId = "userDao")
public interface UserDao {

    @PostMapping(value = "/user/insert")
    ResultInfo insert(@RequestBody UserDTO dto);

    @PostMapping(value = "/user/list")
    ResultInfo list(@RequestBody UserQueryDTO queryDTO);

    @GetMapping(value = "/user/cache/list")
    ResultInfo cacheList();

    @GetMapping(value = "/user/selectById/{id}")
    ResultInfo selectById(@PathVariable("id") Long id);
}
