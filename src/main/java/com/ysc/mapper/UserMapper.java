package com.ysc.mapper;

import com.ysc.pojo.LoginStatus;
import com.ysc.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
//    @Insert("insert into user (account,password,name,create_time,telephone) values (#{account},#{password},#{name},#{creatTime},#{telephone})")
//    void insertUser(String account, String password, String name, LocalDateTime creatTime, String telephone);

    @Insert("insert into user (account,password,name,create_time) values (#{account},#{password},#{name},#{creatTime})")
    void insertUser(String account, String password, String name, LocalDateTime creatTime);

    @Select("SELECT * FROM user WHERE account=#{account};")
    User selectUser(String account);

    @Update("update user set name=#{newNickname} where user_id=#{userId}")
    void updateNickname(Integer userId, String newNickname);

    @Update("update user set avatar=#{newAvatarUrl} where user_id=#{userId}")
    void updateAvatarUrl(Integer userId, String newAvatarUrl);

    @Update("update user set gender=#{gender} where user_id=#{userId}")
    void updateGender(Integer userId, String gender);

    @Select("select * from user where account=#{account}")
    User selectUserByAccountAndPassword(String account);

    @Insert("INSERT INTO login_status (id, user_id) VALUES (#{id}, #{userId})")
    void insertLoginStatus(String id, Integer userId);

    @Select("select * from login_status where id=#{id}")
    LoginStatus selectLoginStatus(String id);

    @Select("select * from user where user_id=#{userId}")
    User selectUserInfoByUserId(Integer userId);
}
