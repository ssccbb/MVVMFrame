package com.sung.mvvmframe.api.server;

/**
 * Create by sung at 2020/9/25
 *
 * @desc:
 * @notice:
 */
public interface ApiConfig {

    String DEFAULT_DOMAIN = "http://jessyan.me";

    class Base {
        public static final String DEFAULT_DEBUG_DOMAIN = "";
        public static final String DEFAULT_RELEASE_DOMAIN = "";
        public static final String DEFAULT_DOMAIN_NAME = "";
    }

    class News {
        public static final String DEFAULT_DOMAIN = "http://v.juhe.cn";
        public static final String DEFAULT_DOMAIN_NAME = "news";
    }
}
