package com.ysc.controller;


import com.ysc.pojo.LoginStatus;
import com.ysc.pojo.Result;
import com.ysc.pojo.User;
import com.ysc.security.JwtService;
import com.ysc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService myService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public Result userLogin(@RequestBody Map<String, String> loginData) {
        String account = loginData.get("account");
        String password = loginData.get("password");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account, password)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(account);
        final String jwt = jwtService.generateToken(userDetails);
//        jwtService.addTokenToBlacklist(jwt);
        return Result.success(jwt);
    }

    @PostMapping("/logout")
    public Result userLogout(@RequestHeader("Authorization") String authHeader) {
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // 搓索引7开始，获取"Bearer "后面的Token
            String jwt = authHeader.substring(7);
            jwtService.addTokenToBlacklist(jwt);
        }
        return Result.success();
    }

    @GetMapping("/check")
    public Result checkIsRegister( String account){
        String isR= String.valueOf(myService.userCheck(account));
        return Result.success(isR);
    }

    @PostMapping("/register")
    public Result userRegister(@RequestBody User user) {
        String account = user.getAccount();
        String password = user.getPassword();
//        String telephone = user.getTelephone();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

//        myService.addUser(account, encodedPassword, telephone);
        myService.addUser(account, encodedPassword);
        return Result.success();
    }

    @GetMapping("/userinfo")
    public Result searchUserId(String account){
        User user=myService.getUserID(account);
        return Result.success(user);
    }

    @PostMapping("/nickname/{userId}")
    public Result modifyNickname(@PathVariable Integer userId, String newNickname){
        myService.changeName(userId,newNickname);
        return Result.success();
    }

    @PostMapping("/avatar/{userId}")
    public Result modifyAvatar(@PathVariable Integer userId ,String newAvatarUrl){
        myService.changeAvatarUrl(userId,newAvatarUrl);
        return Result.success();
    }

    @PostMapping("/gender/{userId}")
    public Result modifyGender(@PathVariable Integer userId,String gender){
        myService.changeGender(userId,gender);
        return Result.success();
    }


    @PostMapping("/login/yunsg/{id}")
    public Result yunsgLogin(@PathVariable String id,@RequestBody Map<String, String> loginData){
        String account = loginData.get("account");
        String password = loginData.get("password");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account, password)
            );
            Integer userId=myService.getUserID(account).getUserId();
            myService.addLoginStatus(id,userId);
            return  Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("Incorrect username or password");
        }
    }

    @GetMapping("/check-login")
    public Result checkLogin(@RequestParam String id) {
        // 通过查询参数获取 uid
//        System.out.println("UID: " + uid);

        // 根据 uid 判断登录状态的逻辑
        LoginStatus loginInfo = myService.searchLoginStatus(id);  // 假设你有个方法来检查登录状态

        if (loginInfo!=null) {
            return Result.success("用户已登录");
        } else {
            return Result.error("用户未登录");
        }
    }

    @GetMapping("/yunsg/userinfo")
    public Result searchYunsgUserInfo(@RequestParam String id){
        Integer userId= myService.searchLoginStatus(id).getUserId();
        User userInfo =myService.getUserInfoByUserId(userId);
        return Result.success(userInfo);
    }
}
