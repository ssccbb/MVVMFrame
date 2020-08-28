package com.sung.mvvmframe.mvvm.view.activity;

import com.sung.common.bean.config.ToolbarConfig;
import com.sung.common.cache.MCache;
import com.sung.mvvmframe.R;
import com.sung.mvvmframe.base.BaseActivity;

public class GuideActivity extends BaseActivity {

    @Override
    protected void set() {
        MCache.setGuideShown(true);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_guide;
    }

    @Override
    protected ToolbarConfig getToolbarConfig() {
        return null;
    }
}