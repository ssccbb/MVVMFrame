package com.sung.mvvmframe.mvvm.view.activity;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import me.sung.base.bean.config.ActionBarConfig;
import com.sung.mvvmframe.GuideActivityBinder;
import com.sung.mvvmframe.R;
import com.sung.mvvmframe.Router;
import com.sung.mvvmframe.base.BaseActivity;

public class GuideActivity extends BaseActivity<GuideActivityBinder> implements View.OnClickListener {
    private int[] mGuidePics = {R.drawable.guide_pic_1,
            R.drawable.guide_pic_2,
            R.drawable.guide_pic_3};

    @Override
    protected void set() {
        setMCache();
        setPager();
    }

    private void setMCache() {
//        MCache.setGuideShown(true);
    }

    private void setPager() {
        ViewPager pager = mBinder.vpPager;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pager.setOffscreenPageLimit(mGuidePics.length);
        pager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView pic = new ImageView(getContext());
                pic.setImageResource(mGuidePics[position]);
                pic.setLayoutParams(params);
                pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(pic);
                return pic;
            }

            @Override
            public int getCount() {
                return mGuidePics.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ((position + 1) == mGuidePics.length) {
                    mBinder.setTips(getString(R.string.sys_enter_hint));
                    mBinder.tvNext.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_theme));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinder.setTips(getString(R.string.sys_next_hint));
        mBinder.piIndicator.setupWithViewPager(pager);
        mBinder.setListener(this);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_guide;
    }

    @Override
    protected ActionBarConfig getActionbarConfig() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                skip();
                break;
            case R.id.tv_next:
                next();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPress() {
    }

    private void skip() {
        goTo();
    }

    private void next() {
        int next = mBinder.vpPager.getCurrentItem() + 1;
        int limit = mGuidePics.length;
        if (next < limit) {
            mBinder.vpPager.setCurrentItem(next);
        } else {
            goTo();
        }
    }

    private void goTo() {
        Router.goIndexActivity(getContext());
        finish();
    }
}