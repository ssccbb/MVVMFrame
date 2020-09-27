package com.sung.mvvmframe.config;

/**
 * Create by sung at 2020/9/27
 *
 * @desc: 组件Moudle初始化
 * @notice:
 */
public interface MoudleConfig {
    String BASE_LIB = "me.sung.base.CommonMoudleApi";
    String NET_LIB = "me.jessyan.net.NetMoudleApi";
    String UIKIT_LIB = "me.sung.uikit.UikitMoudleApi";

    String[] moudles = {BASE_LIB, NET_LIB, UIKIT_LIB};
}
