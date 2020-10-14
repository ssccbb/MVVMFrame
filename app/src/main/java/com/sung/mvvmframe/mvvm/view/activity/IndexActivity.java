package com.sung.mvvmframe.mvvm.view.activity;

import androidx.annotation.NonNull;
import butterknife.OnPageChange;
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

import java.util.List;

public class IndexActivity extends BaseActivity<IndexActivityBinder> implements HomeTabLayout.OnTabSelectListener {
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
        mBinder.htlTabs.addOnTabSelectListener(this::onTabSelect);
        mBinder.htlTabs.setSelect(0, true);
        mBinder.vpPager.setOffscreenPageLimit(tabs.size());
        mBinder.vpPager.setAdapter(mIndexPager);
        mBinder.vpPager.setCurrentItem(0);
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

    @OnPageChange(R.id.vp_pager)
    public void onPageSelect(int position) {
        mBinder.htlTabs.setSelect(position, true);
    }

    @Override
    public void onTabSelect(int position, HomeTabBean tab) {
        mBinder.vpPager.setCurrentItem(position);
    }
}