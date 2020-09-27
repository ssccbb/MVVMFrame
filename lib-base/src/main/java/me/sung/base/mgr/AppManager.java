package me.sung.base.mgr;

/**
 * Create by sung at 2018/11/13
 *
 * @Description:
 */

import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity管理类
 */
public class AppManager {
    private static Stack<AppCompatActivity> activityStack;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        return Holder.instance;
    }

    private static class Holder {
        public static AppManager instance = new AppManager();
    }

    /**
     * 添加BaseActivity到堆栈
     */
    public void addActivity(AppCompatActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<AppCompatActivity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public AppCompatActivity currentActivity() {
        AppCompatActivity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try{
            AppCompatActivity activity = activityStack.lastElement();
            finishActivity(activity);
        }catch (Exception e){
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(AppCompatActivity activity) {
        try {
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }catch (Exception e){
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try{
            for (AppCompatActivity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    finishActivity(activity);
                }
            }
        }catch (Exception e){
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        try{
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }catch (Exception e){
        }
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
