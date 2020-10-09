package com.sung.mvvmframe.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import me.sung.base.bean.config.ActionBarConfig;
import me.sung.base.enums.MenuEnum;
import me.sung.base.mgr.AccountManager;
import me.sung.base.utils.Log;
import me.sung.base.utils.SPUtils;
import me.sung.base.Constants;
import me.sung.base.cache.MCache;

import com.sung.mvvmframe.R;
import com.sung.mvvmframe.Router;
import com.sung.mvvmframe.mvvm.view.activity.IndexActivity;
import com.sung.mvvmframe.recorder.PageFlowRecorder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import me.sung.base.utils.StatusBarUtils;

/**
 * @author: sung
 * @date: 2018/10/15
 * @Description: activity基类
 */
public abstract class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity {
    protected D mBinder;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtils.setTranslucentStatusBar(this);
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
        mBinder = DataBindingUtil.setContentView(this, getLayoutResID());
        ActionBarConfig config = getActionbarConfig();
        if (config != null) {
            acceptActionbarConfig(config);
        }
        getArgument();
        set();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PageFlowRecorder.getInstance().addPageAuto(this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        PageFlowRecorder.getInstance().updatePage(this.getClass().getSimpleName());
    }

    protected void getArgument() {
    }

    protected abstract void set();

    protected abstract int getLayoutResID();

    protected abstract ActionBarConfig getActionbarConfig();

    /**
     * 接入自定义Actionbar的配置
     */
    protected void acceptActionbarConfig(ActionBarConfig config) {
        try {
            if (findViewById(R.id.action_bar) == null) {
                return;
            }
            // 默认主题色以及白色UI
            // 背景色
            findViewById(R.id.action_bar).setBackground(ContextCompat.getDrawable(this, config.getBackgroundColor() == 0 ? R.color.theme_color : config.getBackgroundColor()));
            // 返回布局
            View backContainer = findViewById(R.id.ll_action_back);
            backContainer.setVisibility(config.isDisplayBackButton() ? View.VISIBLE : View.GONE);
            backContainer.setOnClickListener(v -> onBackPress());
            // 返回箭头
            if (config.isDisplayBackIconButton()) {
                ImageView backIcon = findViewById(R.id.iv_action_back);
                backIcon.setVisibility(View.VISIBLE);
                backIcon.setImageResource(config.getBackIcon() == 0 ? R.mipmap.ic_back_white : config.getBackIcon());
            } else {
                findViewById(R.id.iv_action_back).setVisibility(View.GONE);
            }
            // 返回logo
            if (config.isDisplayBackLogoButton()) {
                ImageView backLogo = findViewById(R.id.iv_action_logo);
                backLogo.setVisibility(View.VISIBLE);
                backLogo.setImageResource(config.getBackLogo() == 0 ? R.mipmap.ic_launcher : config.getBackLogo());
            } else {
                findViewById(R.id.iv_action_logo).setVisibility(View.GONE);
            }
            // 返回文字提示
            if (config.isDisplayBackTextButton()) {
                TextView back = findViewById(R.id.tv_action_back);
                back.setVisibility(View.VISIBLE);
                back.setText(TextUtils.isEmpty(config.getBackTips()) ? getString(R.string.sys_back_tipst) : config.getBackTips());
                back.setTextColor(ContextCompat.getColor(this, config.getBackTextColor() == 0 ? R.color.app_white_ff : config.getBackTextColor()));
            } else {
                findViewById(R.id.tv_action_back).setVisibility(View.GONE);
            }
            // 中心标题
            if (config.isDisplayCenterTitle()) {
                TextView title = findViewById(R.id.tv_action_title);
                title.setVisibility(View.VISIBLE);
                title.setTextColor(ContextCompat.getColor(this, config.getTitleColor() == 0 ? R.color.app_white_ff : config.getTitleColor()));
                title.setText(TextUtils.isEmpty(config.getTittle()) ? getString(R.string.app_name) : config.getTittle());
            } else {
                findViewById(R.id.tv_action_title).setVisibility(View.GONE);
            }
            // 副标题
            if (config.isDisplaySubTitle()) {
                TextView subTitle = findViewById(R.id.tv_action_subtitle);
                subTitle.setVisibility(View.VISIBLE);
                subTitle.setTextColor(ContextCompat.getColor(this, config.getSubTitleColor() == 0 ? R.color.app_gray_f5 : config.getSubTitleColor()));
                subTitle.setText(TextUtils.isEmpty(config.getSubTitle()) ? getString(R.string.app_name) : config.getSubTitle());
            } else {
                findViewById(R.id.tv_action_subtitle).setVisibility(View.GONE);
            }
            // 菜单
            if (config.getMenuType() == MenuEnum.Icon) {
                findViewById(R.id.tv_action_menu).setVisibility(View.GONE);
                ImageView iconMenu = findViewById(R.id.iv_action_menu);
                iconMenu.setVisibility(View.VISIBLE);
                iconMenu.setImageResource(config.getMenuIcon() == 0 ? R.mipmap.ic_launcher : config.getMenuIcon());
            } else if (config.getMenuType() == MenuEnum.Text) {
                findViewById(R.id.iv_action_menu).setVisibility(View.GONE);
                TextView textMenu = findViewById(R.id.tv_action_menu);
                textMenu.setVisibility(View.VISIBLE);
                textMenu.setTextColor(ContextCompat.getColor(this, config.getMenuColor() == 0 ? R.color.app_gray_f5 : config.getMenuColor()));
                textMenu.setText(getString(config.getMenuText() == 0 ? R.string.app_name : config.getMenuText()));
            } else {
                findViewById(R.id.iv_action_menu).setVisibility(View.GONE);
                findViewById(R.id.tv_action_menu).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(BaseActivity.class.getSimpleName(), e.toString());
        }
    }

    protected ActionBarConfig getSimpleActionbarConfig(String title, boolean dark) {
        return new ActionBarConfig.Builder()
                // 背景色
                .setBackgroundColor(!dark ? R.color.app_white_ff : R.color.theme_color)
                // 展示返回按钮
                .setDisplayBackButton(true)
                // 展示返回箭头
                .setDisplayBackIconButton(true)
                .setBackIcon(!dark ? R.mipmap.ic_back_black : R.mipmap.ic_back_white)
                // 展示logo
                .setDisplayBackLogoButton(false)
                // 展示返回文字
                .setDisplayBackTextButton(false)
                // 展示中心标题
                .setDisplayCenterTitle(true)
                .setTittle(title)
                .setTitleColor(!dark ? R.color.app_black_ff : R.color.app_white_ff)
                // 展示中心副标题
                .setDisplaySubTitle(false)
                // 菜单类型
                .setMenuType(MenuEnum.None)
                .creat();
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
        return SPUtils.get(MCache.DEFAULT_SP_NAME);
    }

    protected FragmentTransaction getSupportFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

    /**
     * 转换简体中文
     */
    public void changeChinese() {
        SharedPreferences sharedPreferences = getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.CONFIG_LANGUAGE, LanContextWrapper.LANG_CN).apply();
        rebot();
    }

    /**
     * 转换英文
     */
    public void changeEnglish() {
        SharedPreferences sharedPreferences = getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.CONFIG_LANGUAGE, LanContextWrapper.LANG_EN).apply();
        rebot();
    }

    /**
     * 转换日文
     */
    public void changeJapanese() {
        SharedPreferences sharedPreferences = getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.CONFIG_LANGUAGE, LanContextWrapper.LANG_JP).apply();
        rebot();
    }

    /**
     * 转换繁体中文
     */
    public void changeRChinese() {
        SharedPreferences sharedPreferences = getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.CONFIG_LANGUAGE, LanContextWrapper.LANG_HK).apply();
        rebot();
    }

    public void changeKorean() {
        SharedPreferences sharedPreferences = getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.CONFIG_LANGUAGE, LanContextWrapper.LANG_KO).apply();
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
        AccountManager.clear();
        Router.goLoginActivity(this);
    }

}
