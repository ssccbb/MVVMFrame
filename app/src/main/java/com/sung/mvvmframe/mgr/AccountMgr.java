package com.sung.mvvmframe.mgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.sung.mvvmframe.Application;

import java.util.Set;

/**
 * Create by sung at 2020/9/27
 *
 * @desc: 账号管理类
 * @notice:
 */
public class AccountMgr {
    private final static String KEY_ACCOUNT = "key_account";
    private final static String KEY_USER = "key_user_info";

    private static SharedPreferences mAccountPrefrences;

    {
        try {
            Context context = Application.getInstance().getApplicationContext();
            mAccountPrefrences = context.getSharedPreferences(AccountMgr.class.getSimpleName(), Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(AccountMgr.class.getSimpleName(), "initialized error!");
        }
    }

    private AccountMgr() {
        throw new IllegalStateException("Cannot be initialized in this way！");
    }

    public static void setAccount(String account) {
        try {
            putValue(KEY_ACCOUNT, account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAccount() {
        return getValue(KEY_ACCOUNT);
    }

    public static void setUserInfo(String json) {
        try {
            putValue(KEY_USER, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUserInfo() {
        String user = getValue(KEY_USER);
        return TextUtils.isEmpty(user) ? "{}" : user;
    }

    public static void putValue(String key, Object value) throws IllegalStateException {
        if (mAccountPrefrences == null) return;
        if (mAccountPrefrences.contains(key)) {
            mAccountPrefrences.edit().remove(key);
        }
        if (value instanceof String) {
            mAccountPrefrences.edit().putString(key, (String) value);
        } else if (value instanceof Boolean) {
            mAccountPrefrences.edit().putBoolean(key, (boolean) value);
        } else if (value instanceof Float) {
            mAccountPrefrences.edit().putFloat(key, (float) value);
        } else if (value instanceof Integer) {
            mAccountPrefrences.edit().putInt(key, (int) value);
        } else if (value instanceof Long) {
            mAccountPrefrences.edit().putLong(key, (long) value);
        } else if (value instanceof Set) {
            mAccountPrefrences.edit().putStringSet(key, (Set<String>) value);
        } else {
            throw new IllegalStateException("can not apply this type ---> " + value.getClass().getSimpleName());
        }
        mAccountPrefrences.edit().commit();
    }

    public static String getValue(String key) {
        if (mAccountPrefrences != null && mAccountPrefrences.contains(key)) {
            return mAccountPrefrences.getString(key, "");
        }
        return "";
    }

    public static void clear() {
        mAccountPrefrences.edit().clear().commit();
    }
}
