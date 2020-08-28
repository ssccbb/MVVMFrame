package com.sung.common.cache;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.sung.common.Constants;
import com.sung.common.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Create by sung at 2020-04-04
 *
 * @desc: 数据本地化
 * @notice: 一直持有不清空
 */
public class MCache {

    /**
     * 夜览模式开关
     */
    public static boolean setNightMode(boolean isOpen) {
        try {
            SharedPreferences.Editor edit = SPUtils.get(Constants.DEFAULT_SP_NAME).edit();
            edit.putBoolean(Constants.Config.CONFIG_NIGHT_MODE, isOpen);
            return edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return 夜览模式
     */
    public static boolean isNighrModeOpen() {
        try {
            return SPUtils.get(Constants.DEFAULT_SP_NAME).getBoolean(Constants.Config.CONFIG_NIGHT_MODE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存登录信息
     */
    public static boolean setLogin(String login) {
        try {
            SharedPreferences.Editor edit = SPUtils.get(Constants.MCache.LOGIN).edit();
            edit.putString(Constants.MCache.LOGIN_INFO, login);
            return edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 清除登录信息
     */
    public static boolean clearLogin() {
        try {
            return SPUtils.clear(Constants.MCache.LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取已登录账号
     */
    public static String getAccount() {
        try {
            JSONObject cache = getLoginCache();
            return cache.getString(Constants.MCache.LOGIN_ACCOUNT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取已登录账号的token
     */
    public static String getToken() {
        try {
            JSONObject cache = getLoginCache();
            return cache.getString(Constants.MCache.LOGIN_TOKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static JSONObject getLoginCache() throws JSONException {
        SharedPreferences sp = SPUtils.get(Constants.MCache.LOGIN);
        String json = sp.getString(Constants.MCache.LOGIN_INFO, "");
        return new JSONObject(json);
    }

    /**
     * 是否登录状态
     */
    public static boolean isLogin() {
        SharedPreferences sp = SPUtils.get(Constants.MCache.LOGIN);
        if (sp != null) {
            String saved = sp.getString(Constants.MCache.LOGIN_INFO, "");
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
        }
        return false;
    }
}
