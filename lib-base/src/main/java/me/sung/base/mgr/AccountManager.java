package me.sung.base.mgr;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import me.sung.base.bean.UserModel;
import me.sung.base.utils.JsonParser;
import me.sung.base.utils.SPUtils;

/**
 * Create by sung at 2020/9/27
 *
 * @desc: 账号管理类
 * @notice:
 */
public class AccountManager {
    public static final String LOGIN = "sp_login";
    public static final String LOGIN_INFO = "login_info";
    public static final String LOGIN_ACCOUNT = "login_account";
    public static final String LOGIN_TOKEN = "login_token";

    private static SharedPreferences mAccountPrefrences;

    {
        try {
            mAccountPrefrences = SPUtils.get(LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(AccountManager.class.getSimpleName(), "initialized error!");
        }
    }

    private AccountManager() {
        throw new IllegalStateException("Cannot be initialized in this way！");
    }

    public static void saveToken(String token) {
        mAccountPrefrences.edit().putString(LOGIN_TOKEN, token).commit();
    }

    /**
     * 获取已登录账号的token
     */
    public static String getToken() {
        return mAccountPrefrences.getString(LOGIN_TOKEN, "");
    }

    public static void saveAccount(String account) {
            mAccountPrefrences.edit().putString(LOGIN_ACCOUNT, account).commit();
    }

    /**
     * 获取已登录账号
     */
    public static String getAccount() {
        return mAccountPrefrences.getString(LOGIN_ACCOUNT, "");
    }

    public static void saveUser(UserModel userModel) {
        mAccountPrefrences.edit().putString(LOGIN_INFO, userModel.toString()).commit();
    }

    /**
     * 获取保存的登陆账户 #UserModel
     */
    public static UserModel getUser() {
        String json = mAccountPrefrences.getString(LOGIN_INFO, "");
        if (TextUtils.isEmpty(json)) {
            return new UserModel();
        }
        return JsonParser.gson.fromJson(json,UserModel.class);
    }

    /**
     * 是否登录状态
     */
    public boolean hasLogin() {
        String saved = mAccountPrefrences.getString(LOGIN_INFO, "");
        if (TextUtils.isEmpty(saved)) {
            return false;
        }
        try {
            JSONObject object = new JSONObject(saved);
            Iterator<String> keys = object.keys();
            if (keys.hasNext()) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 清除登录信息
     */
    public static void clear() {
        SPUtils.clear(LOGIN);
    }
}
