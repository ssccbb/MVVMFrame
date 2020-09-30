package me.sung.base.cache;

import android.content.SharedPreferences;
import me.sung.base.Constants;
import me.sung.base.utils.SPUtils;

/**
 * Create by sung at 2020-04-04
 *
 * @desc: 数据本地化
 * @notice: 一直持有不清空
 */
public class MCache {
    public static final String DEFAULT_SP_NAME = "com.sung.mvvmframe.sp";

    public static final String GUIDE_HAS_SHOW = "has_show_guide";

    public static boolean setNightMode(boolean isOpen) {
        try {
            SharedPreferences.Editor edit = SPUtils.get(DEFAULT_SP_NAME).edit();
            edit.putBoolean(Constants.CONFIG_NIGHT_MODE, isOpen);
            return edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return 夜览模式
     */
    public static boolean isNightModeOpen() {
        try {
            return SPUtils.get(DEFAULT_SP_NAME).getBoolean(Constants.CONFIG_NIGHT_MODE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean setGuideShown(boolean shown) {
        try {
            SharedPreferences.Editor edit = SPUtils.get(DEFAULT_SP_NAME).edit();
            edit.putBoolean(GUIDE_HAS_SHOW, shown);
            return edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return 获取引导页是否展示
     * */
    public static boolean isGuideShown() {
        try {
            return SPUtils.get(DEFAULT_SP_NAME).getBoolean(GUIDE_HAS_SHOW, false);
        } catch (Exception e) {
            return false;
        }
    }
}
