package com.sung.mvvmframe.api;

/**
 * Create by sung at 2020/9/25
 *
 * @desc:
 * @notice:
 */
interface Api {

    String DEFAULT_DOMAIN = "http://jessyan.me";

    class Base {
        public static String DEFAULT_DEBUG_DOMAIN = "";
        public static String DEFAULT_RELEASE_DOMAIN = "";
        public static String DEFAULT_DOMAIN_NAME = "";
    }

    class Douban {
        public static String DEFAULT_DOMAIN = "";
        public static String DEFAULT_DOMAIN_NAME = "";
    }
}
