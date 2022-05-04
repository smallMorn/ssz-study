package com.ssz.api.controller;

import com.ssz.api.servcie.UserService;
import com.ssz.common.model.dto.UserDTO;
import com.ssz.common.model.dto.UserQueryDTO;
import com.ssz.common.web.enumerate.ApiCode;
import com.ssz.common.web.result.ResultInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/insert")
    public ResultInfo insert(@RequestBody UserDTO dto) {
        long millis = System.currentTimeMillis();
        userService.insert(dto);
        log.info("插入总耗时:{}", System.currentTimeMillis() - millis);
        return ResultInfo.success();
    }

    @PostMapping("list")
    public ResultInfo list(@RequestBody UserQueryDTO queryDTO) {
        if (Objects.isNull(queryDTO.getPageCurrent()) || queryDTO.getPageCurrent() < 0) {
            return ResultInfo.fail(ApiCode.ILLEGAL_PARAMETER);
        }
        return userService.list(queryDTO);
    }

    @GetMapping("/cache/list")
    public ResultInfo cacheList(){
        return userService.cacheList();
    }

    @GetMapping("/selectById")
    public ResultInfo selectById(Long id){
        return userService.selectById(id);
    }
}
