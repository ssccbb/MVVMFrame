package com.sung.mvvmframe.mvvm.view.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.sung.mvvmframe.IndexFragmentBinder;
import com.sung.mvvmframe.R;
import com.sung.mvvmframe.base.BaseFragment;
import com.sung.mvvmframe.mvvm.viewmodel.BaseViewModel;

import me.sung.base.enums.TabTypeEnum;
import me.sung.base.utils.SnackbarUtils;

/**
 * Create by sung at 2020/10/14
 *
 * @desc:
 * @notice:
 */
public class IndexFragment extends BaseFragment<BaseViewModel, IndexFragmentBinder> implements View.OnClickListener {

    public static IndexFragment newInstance(TabTypeEnum typeEnum) {
        Bundle data = new Bundle();
        data.putString(IndexFragment.class.getSimpleName(), typeEnum.name());
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void set() {
        mBinder.setTest(mBundleData.getString(getSimpleName()));
        mBinder.setClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SnackbarUtils.Short(mRoot, String.format("%s%s", getString(R.string.app_name), mBinder.getTest()))
                .gravityFrameLayout(Gravity.BOTTOM).show();
    }
}
