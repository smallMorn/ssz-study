package com.ssz.api.servcie.impl;

import com.ssz.api.servcie.UserService;
import com.ssz.api.dao.ProductDao;
import com.ssz.common.model.dto.UserDTO;
import com.ssz.api.dao.UserDao;
import io.seata.core.context.RootContext;
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
        log.info("xid:{}", RootContext.getXID());
        userDao.insert(dto);
        productDao.deleteByProductId(dto.getProductId());
        return true;
    }
}
