package com.sung.mvvmframe.recorder;

import com.sung.mvvmframe.mvvm.view.activity.IndexActivity;
import com.sung.mvvmframe.mvvm.view.activity.SplashActivity;

/**
 * Create by sung at 2020/7/30
 *
 * @desc: 历史页面定义
 * @notice:
 */
public enum PageFlowName {
    HomePage(IndexActivity.class.getSimpleName()),
    SplashPage(SplashActivity.class.getSimpleName());

    private String value;

    PageFlowName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
