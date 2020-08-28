package com.sung.mvvmframe;

import android.content.Context;
import android.content.Intent;

import com.sung.mvvmframe.mvvm.view.activity.GuideActivity;
import com.sung.mvvmframe.mvvm.view.activity.IndexActivity;
import com.sung.mvvmframe.mvvm.view.activity.LoginActivity;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class Router {

    public static void goLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void goIndexActivity(Context context) {
        context.startActivity(new Intent(context, IndexActivity.class));
    }

    public static void goGuideActivity(Context context) {
        context.startActivity(new Intent(context, GuideActivity.class));
    }
}
