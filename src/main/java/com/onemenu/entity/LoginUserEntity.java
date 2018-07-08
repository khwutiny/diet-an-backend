package com.onemenu.entity;

import java.util.Arrays;

public class LoginUserEntity {
    private String login_user_id;
    private String login_user_account;
    private String login_user_password;
    private String[] user_ids;

    public String[] getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String[] user_ids) {
        this.user_ids = user_ids;
    }

    public String getLogin_user_id() {
        return login_user_id;
    }

    public void setLogin_user_id(String login_user_id) {
        this.login_user_id = login_user_id;
    }

    public String getLogin_user_account() {
        return login_user_account;
    }

    public void setLogin_user_account(String login_user_account) {
        this.login_user_account = login_user_account;
    }

    public String getLogin_user_password() {
        return login_user_password;
    }

    @Override
    public String toString() {
        return "LoginUserEntity{" +
                "login_user_id='" + login_user_id + '\'' +
                ", login_user_account='" + login_user_account + '\'' +
                ", login_user_password='" + login_user_password + '\'' +
                ", user_ids=" + Arrays.toString(user_ids) +
                '}';
    }

    public void setLogin_user_password(String login_user_password) {
        this.login_user_password = login_user_password;
    }
}
