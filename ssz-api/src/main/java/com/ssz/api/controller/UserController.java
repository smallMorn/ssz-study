package com.ssz.api.controller;

import com.ssz.api.servcie.UserService;
import com.ssz.common.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/insert")
    public Boolean insert(@RequestBody  UserDTO dto){
        return userService.insert(dto);
    }
}
