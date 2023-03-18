package com.IonMiddelraad.iprwcbackend.service;

import com.IonMiddelraad.iprwcbackend.dao.UserDAO;
import com.IonMiddelraad.iprwcbackend.model.User;
import com.IonMiddelraad.iprwcbackend.model.UserInfo;

public class UserInfoService {
    private UserInfo newUserInfo = null;
    private UserDAO userDAO;

    public UserInfo toUserInfo(User user) {
        this.newUserInfo.setId(user.getId());
        this.newUserInfo.setName(user.getName());
        this.newUserInfo.setEmail(user.getEmail());
        return this.newUserInfo;
    }

    public User toUserInfo(UserInfo userInfo) {
        User originalEmployee = this.userDAO.show(userInfo.getId());
        return originalEmployee;
    }
}
