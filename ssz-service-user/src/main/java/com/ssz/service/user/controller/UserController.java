package com.ssz.service.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssz.common.model.dto.UserDTO;
import com.ssz.common.model.dto.UserQueryDTO;
import com.ssz.common.web.result.ResultInfo;
import com.ssz.service.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final IUserService userService;

    @PostMapping(value = "/insert")
    public Boolean insert(@RequestBody UserDTO dto){
        return userService.insert(dto);
    }

    @PostMapping(value = "/list")
    public ResultInfo list(@RequestBody UserQueryDTO queryDTO){
        Page page = userService.list(queryDTO);
        return ResultInfo.success(page);
    }
}
