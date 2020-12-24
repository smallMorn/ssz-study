package com.ssz.service.user.service.impl;

import com.ssz.service.user.entity.User;
import com.ssz.service.user.mapper.UserMapper;
import com.ssz.service.user.service.UserService;
import com.ssz.common.model.dto.UserDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @GlobalTransactional
    public Boolean insert(UserDTO dto) {
        System.out.println();
        userMapper.insert(new User().setUserName(dto.getUserName())
                .setUserSex(dto.getUserSex()).setUserAge(dto.getUserAge()));
        return true;
    }
}
