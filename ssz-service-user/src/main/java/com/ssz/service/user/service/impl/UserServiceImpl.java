package com.ssz.service.user.service.impl;

import com.ssz.service.user.entity.User;
import com.ssz.service.user.mapper.UserMapper;
import com.ssz.service.user.service.UserService;
import com.ssz.common.model.dto.UserDTO;
import io.seata.core.context.RootContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional
    public Boolean insert(UserDTO dto) {
        userMapper.insert(new User().setUserName(dto.getUserName())
                .setUserSex(dto.getUserSex()).setUserAge(dto.getUserAge()));
        return true;
    }
}
