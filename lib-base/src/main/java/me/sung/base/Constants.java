package me.sung.base;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class Constants {
    public static final String TAG = "me.sung.base";

    public static final String DEFAULT_SP_NAME = "com.sung.mvvmframe.sp";
    public static final String TEMP_SP_NAME = "com.sung.mvvmframe.tempsp";

    public class MCache {
        public static final String LOGIN = "sp_login";
        public static final String LOGIN_INFO = "login_info";
        public static final String LOGIN_ACCOUNT = "login_account";
        public static final String LOGIN_TOKEN = "login_token";
        public static final String GUIDE_HAS_SHOW = "has_show_guide";
    }

    public class MTempCache {
        public static final String TEMP_LOGIN_ACCOUNT = "temp_login_account";
    }

    public class Config {
        public static final int CONFIG_FRESCO_CACHE_SIZE = 30;// 兆节
        public static final String CONFIG_FRESCO_CACHE_DIR = "/cache/fresco";
        public static final int CONFIG_SPLASH_SKIP_TIME = 2000;
        public static final int CONFIG_INDEX_EXIT_TIME = 2000;
        public static final int CONFIG_VERTIFY_WAIT_TIME = 60 * 1000;
        public static final String CONFIG_LANGUAGE = "com.sung.sp.language";
        public static final String CONFIG_NIGHT_MODE = "config.nightmode";
    }
}
