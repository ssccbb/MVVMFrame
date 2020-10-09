package com.sung.mvvmframe.mvvm.view.activity;

import me.sung.base.bean.config.ActionBarConfig;
import com.sung.mvvmframe.R;
import com.sung.mvvmframe.base.BaseActivity;

public class IndexActivity extends BaseActivity {

    @Override
    protected void set() {

    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_index;
    }

    @Override
    protected ActionBarConfig getActionbarConfig() {
        return getSimpleActionbarConfig(getString(R.string.app_name),true);
    }
}