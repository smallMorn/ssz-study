package com.ssz.api.servcie;

import com.ssz.common.model.dto.UserDTO;
import com.ssz.common.model.dto.UserQueryDTO;
import com.ssz.common.web.result.ResultInfo;

public interface UserService {

    Boolean insert(UserDTO dto);

    ResultInfo list(UserQueryDTO queryDTO);

    ResultInfo cacheList();

}
