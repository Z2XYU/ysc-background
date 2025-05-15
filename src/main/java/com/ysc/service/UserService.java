package com.ysc.service;

import com.ysc.pojo.LoginStatus;
import com.ysc.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

//    void addUser(String account, String password, String telephone);
    void addUser(String account, String password);


    Boolean userCheck(String account);

    User getUserInfo(String account);

    void changeName(Integer userId, String newNickname);

    void changeAvatarUrl(Integer userId, String newAvatarUrl);

    void changeGender(Integer userId, String gender);

    User getUserID(String account);

    void addLoginStatus(String id, Integer userId);

    LoginStatus searchLoginStatus(String id);

    User getUserInfoByUserId(Integer userId);
}
