package com.ssz.api.servcie.impl;

import com.ssz.api.servcie.UserService;
import com.ssz.api.dao.ProductDao;
import com.ssz.common.model.dto.UserDTO;
import com.ssz.api.dao.UserDao;
import com.ssz.common.model.dto.UserQueryDTO;
import com.ssz.common.web.result.ResultInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ProductDao productDao;

    @Override
    @GlobalTransactional
    public Boolean insert(UserDTO dto) {
        userDao.insert(dto);
        productDao.deleteByProductId(dto.getProductId());
        return true;
    }

    @Override
    public ResultInfo list(UserQueryDTO queryDTO) {
        return userDao.list(queryDTO);
    }

    @Override
    public ResultInfo cacheList() {
        return userDao.cacheList();
    }

    @Override
    public ResultInfo selectById(Long id) {
        return userDao.selectById(id);
    }
}
