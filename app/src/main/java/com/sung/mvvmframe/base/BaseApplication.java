package com.sung.mvvmframe.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import me.sung.base.mgr.AppManager;
import me.sung.base.utils.Log;
import com.sung.mvvmframe.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {
    protected Context context;
    protected SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            registerActivityLifecycleCallbacks(this);
            context = getApplicationContext();
        } catch (Exception e) {
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.saveOperationIntoLocal(activity + " --> onActivityCreated");
        if (activity instanceof AppCompatActivity) {
            AppManager.getAppManager().addActivity((AppCompatActivity) activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        Log.saveOperationIntoLocal(activity+" --> onActivityStarted");
    }

    /**
     * life cycle callback
     */
    @Override
    public void onActivityResumed(Activity activity) {
//        Log.saveOperationIntoLocal(activity+" --> onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.saveOperationIntoLocal(activity + " --> onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        Log.saveOperationIntoLocal(activity+" --> onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//        Log.saveOperationIntoLocal(activity+" --> onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.saveOperationIntoLocal(activity + " --> onActivityDestroyed");
        if (activity instanceof AppCompatActivity) {
            AppManager.getAppManager().finishActivity((AppCompatActivity) activity);
        }
        System.gc();
    }

    /**
     * @return 日志默认存储路径
     */
    public String getLogPath() {
        return getExternalCacheDir() + "/app.log";
    }

    /**
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /**
     * @return packagename
     */
    public String getAppPackageName() {
        return getPackageName();
    }

    /**
     * @return sp
     */
    public SharedPreferences getPreferences() {
        if (mPreferences == null) {
            mPreferences = getSharedPreferences(getAppPackageName(), MODE_PRIVATE);
        }
        return mPreferences;
    }
}
