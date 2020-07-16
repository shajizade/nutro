package ir.haji.nutro.panel.um.dto;

import ir.haji.nutro.panel.um.entity.User;

/**
 * Created by Saeedon 8/6/2019.
 */
public class UserAroundDto {
    User user;
    String password;
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
