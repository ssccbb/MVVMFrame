package com.sung.mvvmframe;

import com.sung.mvvmframe.base.BaseApplication;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class Application extends BaseApplication {

    private static class Holder {
        private static Application holder = new Application();
    }

    public static Application getInstance() {
        return Holder.holder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
