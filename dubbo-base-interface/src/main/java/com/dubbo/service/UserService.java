package com.dubbo.service;

import com.dubbo.bean.UserAddress;

import java.util.List;

public interface UserService {
    /**
     *
     * @param userId
     * @return
     */
    public List<UserAddress> getUserAddressList(String userId);
}
