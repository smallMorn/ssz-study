package com.ssz.api.controller;

import com.ssz.api.servcie.UserService;
import com.ssz.common.model.dto.UserDTO;
import com.ssz.common.model.dto.UserQueryDTO;
import com.ssz.common.web.enumerate.ApiCode;
import com.ssz.common.web.result.ResultInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/insert")
    public Boolean insert(@RequestBody UserDTO dto) {
        return userService.insert(dto);
    }

    @PostMapping("list")
    public ResultInfo list(@RequestBody UserQueryDTO queryDTO) {
        if (Objects.isNull(queryDTO.getPageCurrent()) || queryDTO.getPageCurrent() < 0) {
            return ResultInfo.fail(ApiCode.ILLEGAL_PARAMETER);
        }
        return userService.list(queryDTO);
    }
}
