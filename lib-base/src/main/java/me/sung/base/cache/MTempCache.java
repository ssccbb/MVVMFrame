package me.sung.base.cache;

import android.content.SharedPreferences;

import java.util.HashSet;
import me.sung.base.utils.SPUtils;

/**
 * Create by sung at 2020-04-04
 *
 * @desc: 本地化数据
 * @notice: 程序退出清空
 * @tips: SPUtils.put() & SPUtils.remove() & SPUtils.getValue()
 *        都是针对永久存储的操作 临时存储不可使用这类方法
 */
public class MTempCache {
    public static final String TEMP_SP_NAME = "com.sung.mvvmframe.tempsp";

    public static final String TEMP_LOGIN_ACCOUNT = "temp_login_account";
    public static final String TEMP_NET_COOKIES = "temp_cookies";

    /**
     * 清空temp cache
     */
    public static void clear(){
        SPUtils.clear(TEMP_SP_NAME);
    }

    /**
     * 临时存储登录账号
     * */
    public static boolean setCookies(HashSet<String> cookies){
        SharedPreferences.Editor edit = SPUtils.get(TEMP_SP_NAME).edit();
        edit.putStringSet(TEMP_NET_COOKIES,cookies);
        return edit.commit();
    }

    /**
     * 获取临时存储的登录账号
     * */
    public static HashSet<String> getCookies(){
        try {
            return (HashSet<String>) SPUtils.get(TEMP_SP_NAME).getStringSet(TEMP_NET_COOKIES, new HashSet<>());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new HashSet<>();
    }
}
