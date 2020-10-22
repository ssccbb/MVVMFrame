package com.sung.mvvmframe.mvvm.view.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import me.sung.base.bean.HomeTabBean;
import me.sung.base.bean.config.ActionBarConfig;
import me.sung.base.enums.MenuEnum;
import me.sung.base.enums.TabTypeEnum;
import me.sung.uikit.widget.HomeTabLayout;

import com.sung.mvvmframe.IndexActivityBinder;
import com.sung.mvvmframe.R;
import com.sung.mvvmframe.adapter.IndexPagerAdapter;
import com.sung.mvvmframe.base.BaseActivity;
import com.sung.mvvmframe.base.BaseFragment;
import com.sung.mvvmframe.mvvm.view.fragment.IndexFragment;
import com.sung.mvvmframe.mvvm.viewmodel.BaseViewModel;

import java.util.List;

public class IndexActivity extends BaseActivity<BaseViewModel, IndexActivityBinder>
        implements HomeTabLayout.OnTabSelectListener, ViewPager.OnPageChangeListener {
    private IndexPagerAdapter<HomeTabBean, BaseFragment> mIndexPager;

    @Override
    protected void set() {
        final List<HomeTabBean> tabs = mBinder.htlTabs.getTabs();
        mIndexPager = new IndexPagerAdapter<HomeTabBean, BaseFragment>(getSupportFragmentManager(), tabs) {
            @NonNull
            @Override
            public BaseFragment createFragment(int position, HomeTabBean data) {
                return creatFragment(data.getmTabType());
            }
        };

        mBinder.htlTabs.setSelect(0, true);
        mBinder.vpPager.setOffscreenPageLimit(tabs.size());
        mBinder.vpPager.setAdapter(mIndexPager);
        mBinder.vpPager.setCurrentItem(0);

        mBinder.htlTabs.addOnTabSelectListener(this::onTabSelect);
        mBinder.vpPager.addOnPageChangeListener(this);
    }

    private BaseFragment creatFragment(TabTypeEnum typeEnum) {
        return IndexFragment.newInstance(typeEnum);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_index;
    }

    @Override
    protected ActionBarConfig getActionbarConfig() {
        return new ActionBarConfig.Builder()
                .setBackgroundColor(R.color.theme_color)
                .setDisplayBackButton(false)
                .setDisplayCenterTitle(true)
                .setTittle(getString(R.string.app_name))
                .setTitleColor(R.color.app_white_ff)
                .setDisplaySubTitle(false)
                .setMenuType(MenuEnum.None)
                .creat();
    }

    @Override
    public void onTabSelect(int position, HomeTabBean tab) {
        mBinder.vpPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBinder.htlTabs.setSelect(position, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}