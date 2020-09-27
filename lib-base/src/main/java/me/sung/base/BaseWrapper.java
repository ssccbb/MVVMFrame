package me.sung.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by sung at 2020/9/27
 *
 * @desc: 各模块相关接口定义（强制规范Moudle初始化方式）
 * @notice: 子Moudle管理类必须实现该接口统一Context
 */
public interface BaseWrapper {

    void attach(Context context);

    Context getContext();

    SharedPreferences getSharedPreferences();

    String getAppPackageName();

    String getLogPath();

}
