package com.sung.mvvmframe.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sung.common.bean.config.ToolbarConfig;
import com.sung.common.utils.ScreenUtils;
import com.sung.common.Constants;
import com.sung.common.cache.MCache;
import com.sung.mvvmframe.Application;
import com.sung.mvvmframe.R;
import com.sung.mvvmframe.Router;
import com.sung.mvvmframe.mvvm.view.IndexActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;

/**
 * @author: sung
 * @date: 2018/10/15
 * @Description: activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = LanContextWrapper.wrap(newBase);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        acceptToolbarConfig(getToolbarConfig());
        getArgument();
        set();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void getArgument(){
    }

    protected abstract void set();

    protected abstract int getLayoutResID();

    protected abstract ToolbarConfig getToolbarConfig();

    /**
     * 接入Toolbar的配置
     *
     * @notice toolbar的加入使用的是application注册lifecycle的方式
     * 此方法调用需要在onStart()之后
     */
    protected void acceptToolbarConfig(ToolbarConfig config) {
        if (this.getSupportActionBar() == null) {
            return;
        }
        try {
            ActionBar bar = getSupportActionBar();
            bar.setDisplayHomeAsUpEnabled(config.isDisplayBackAsUpEnable());
            Drawable back = getResources().getDrawable(config.getResBackButton() == -99 ? R.mipmap.ic_back_white : config.getResBackButton());
            bar.setHomeAsUpIndicator(back);
            bar.setDisplayShowTitleEnabled(config.isDisplayTitleEnable());
            bar.setDisplayUseLogoEnabled(config.isDisplayLogoEnable());
            bar.setSubtitle(config.isDisplaySubTitleEnable() ? config.getTextSubTitle() : "");
            bar.setTitle(config.getTextTitle());
            if (config.getResLogo() != -99) {
                bar.setLogo(config.getResLogo());
            }
            if (config.isDisplayCenterTitleEnable()) {
                bar.setDisplayShowTitleEnabled(false);
                bar.setSubtitle("");
                if (this.findViewById(R.id.tv_title) != null) {
                    TextView centerTitle = this.findViewById(R.id.tv_title);
                    centerTitle.setVisibility(View.VISIBLE);
                    centerTitle.setText(config.getTextTitle());
                    centerTitle.setTextSize(20);
                    if (config.getColorText() != -99) {
                        centerTitle.setTextColor(config.getColorText());
                    }
                }
            }
            if (config.getColorBackground() != -99) {
                bar.setBackgroundDrawable(getResources().getDrawable(config.getColorBackground()));
            }
            if (config.isDisplayElevationEnable()) {
                bar.setElevation(config.isDisplayElevationEnable() ? ScreenUtils.dip2px(5, this) : 0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected <T extends BaseFragment> T findFragment(int id) {
        return (T) getSupportFragmentManager().findFragmentById(id);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected <T extends BaseFragment> T findFragment(String tag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }

    protected Context getContext() {
        return this;
    }

    /**
     * default preferences
     */
    protected SharedPreferences getPreferences() {
        return Application.getInstance().getPreferences();
    }

    protected FragmentTransaction getSupportFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

    /**
     * 转换简体中文
     */
    public void changeChinese() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.Config.CONFIG_LANGUAGE, LanContextWrapper.LANG_CN).apply();
        rebot();
    }

    /**
     * 转换英文
     */
    public void changeEnglish() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.Config.CONFIG_LANGUAGE, LanContextWrapper.LANG_EN).apply();
        rebot();
    }

    /**
     * 转换日文
     */
    public void changeJapanese() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.Config.CONFIG_LANGUAGE, LanContextWrapper.LANG_JP).apply();
        rebot();
    }

    /**
     * 转换繁体中文
     */
    public void changeRChinese() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.Config.CONFIG_LANGUAGE, LanContextWrapper.LANG_HK).apply();
        rebot();
    }

    public void changeKorean() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.Config.CONFIG_LANGUAGE, LanContextWrapper.LANG_KO).apply();
        rebot();
    }

    /**
     * 重启至主页
     */
    private void rebot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(this, IndexActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            recreate();
        }
    }

    public void onBackPress() {
        try {
            super.onBackPressed();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新登录
     */
    protected void relogin() {
        MCache.clearLogin();
        Router.goLoginActivity(this);
    }

}
