package com.sung.mvvmframe.api;

/**
 * Create by sung at 2020/9/25
 *
 * @desc:
 * @notice:
 */
public interface Api {

    String DEFAULT_DOMAIN = "http://jessyan.me";

    public class Base {
        public static final String DEFAULT_DEBUG_DOMAIN = "";
        public static final String DEFAULT_RELEASE_DOMAIN = "";
        public static final String DEFAULT_DOMAIN_NAME = "";
    }

    public class Weather {
        public static final String DEFAULT_DOMAIN = "http://v.juhe.cn";
        public static final String DEFAULT_DOMAIN_NAME = "weather";
    }
}
