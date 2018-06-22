package com.onemenu.mapper;

import com.onemenu.entity.LoginUserEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginUserMapper {
    @Insert("INSERT INTO LOGIN_USER(login_user_account,login_user_password) VALUES(#{login_user_account}, #{login_user_password})")
    void insert(LoginUserEntity LoginUser);

    @Select("SELECT * FROM LOGIN_USER where login_user_account=#{login_user_account} ")
    @Results({
            @Result(property = "login_user_id", column = "login_user_id"),
            @Result(property = "login_user_account", column = "login_user_account"),
            @Result(property = "login_user_password", column = "login_user_password")
    })
    LoginUserEntity getUser(String login_user_account);



}
