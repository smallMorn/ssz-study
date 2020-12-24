package com.ssz.api.controller;

import com.ssz.api.servcie.UserService;
import com.ssz.common.model.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增用户接口",notes = "新增用户接口")
    public Boolean insert(@RequestBody @Valid UserDTO dto){
        return userService.insert(dto);
    }
}
