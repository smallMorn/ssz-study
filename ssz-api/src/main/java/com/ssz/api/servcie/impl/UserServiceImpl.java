package com.ssz.api.servcie.impl;

import com.ssz.api.servcie.UserService;
import com.ssz.api.dao.ProductDao;
import com.ssz.common.model.dto.UserDTO;
import com.ssz.api.dao.UserDao;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
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

    public static void main(String[] args) {
        String str[] = {"f1low","flo","flo1"};
        Boolean flag = false;
        int index = 0 ;
        int minLength = 0;
        int num = 0;
        for(int i = 0;i < str.length-1;i++){
            if (str[i].length()>str[i+1].length()){
                minLength = str[i+1].length();
            }else {
                minLength = str[i].length();
            }
        }
        System.out.println("最小元素长度"+minLength);
        //拿到最小长度  作为比较次数
        while (num < minLength){
            for (int i = 0 ;i < str.length-1;i++){
                if (str[i].charAt(num) == str[i+1].charAt(num)){
                    flag = true;
                }else {
                    flag = false;
                    break;
                }
            }
            num++;
            if (flag){
                index++;
            }
        }
        System.out.println("相同元素个数:"+index+"相同字符串是:"+str[0].substring(0,index));
    }
}
