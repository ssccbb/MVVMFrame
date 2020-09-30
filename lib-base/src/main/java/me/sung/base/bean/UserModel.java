package me.sung.base.bean;

import me.sung.base.bean.response.LoginResponse;
import me.sung.base.utils.JsonParser;

/**
 * Create by sung at 2020/9/29
 *
 * @desc: 本地使用的用户信息封装类
 * @notice: 从服务端拿到登陆信息之后需要经过一道转换成 #UserModel.parserTo 本地使用类型均为此
 */
public class UserModel extends BaseModel {
    private String account;
    private String token;
    private long uid;

    public static UserModel parserTo(LoginResponse response){
        UserModel user = new UserModel();
        user.setAccount(response.account);
        user.setToken(response.token);
        user.setUid(response.uid);
        return user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return JsonParser.gson.toJson(this);
    }
}
