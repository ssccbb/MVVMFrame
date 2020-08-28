package com.sung.common.cache;

import android.content.SharedPreferences;

import com.sung.common.Constants;
import com.sung.common.utils.SPUtils;

/**
 * Create by sung at 2020-04-04
 *
 * @desc: 本地化数据
 * @notice: 程序退出清空
 */
public class MTempCache {

    /**
     * 清空temp cache
     */
    public static void clear(){
        SPUtils.clear(Constants.TEMP_SP_NAME);
    }

    /**
     * 临时存储登录账号
     * */
    public static boolean setTempAccount(String account){
        SharedPreferences.Editor edit = SPUtils.get(Constants.TEMP_SP_NAME).edit();
        edit.putString(Constants.MTempCache.TEMP_LOGIN_ACCOUNT,account);
        return edit.commit();
    }

    /**
     * 获取临时存储的登录账号
     * */
    public static String getTempAccount(){
        try {
            return SPUtils.get(Constants.TEMP_SP_NAME).getString(Constants.MTempCache.TEMP_LOGIN_ACCOUNT, "");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
