package com.ssz.service.user.controller;

import com.ssz.common.model.dto.UserDTO;
import com.ssz.service.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
@Api(value = "用户服务模块")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增用户接口",notes = "新增用户接口",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Boolean insert(@RequestBody UserDTO dto){
        return userService.insert(dto);
    }
}
