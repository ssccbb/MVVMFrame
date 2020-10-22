package com.sung.mvvmframe.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sung.mvvmframe.mvvm.viewmodel.BaseViewModel;
import com.sung.mvvmframe.recorder.PageFlowRecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.sung.base.cache.MCache;
import me.sung.base.utils.SPUtils;

/**
 * @author: sung
 * @date: 2018/10/15
 * @Description: fragment基类
 */
public abstract class BaseFragment<M extends BaseViewModel, D extends ViewDataBinding> extends Fragment {
    private Unbinder unbinder;
    protected Bundle mBundleData;
    protected D mBinder;
    protected M mModel;
    protected View mRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != -1) {
            mBinder = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            mRoot = mBinder.getRoot();
        } else {
            mRoot = super.onCreateView(inflater, container, savedInstanceState);
        }
        if (mRoot != null) {
            unbinder = ButterKnife.bind(this, mRoot);
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mBundleData = getArguments();
        }
        set();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            PageFlowRecorder.getInstance().addPageAuto(this.getClass().getSimpleName());
        }
    }

    protected abstract int getLayoutId();

    protected abstract void set();

    protected SharedPreferences getPreferences() {
        return SPUtils.get(MCache.DEFAULT_SP_NAME);
    }

    protected FragmentManager getSupportFragmentManager() {
        return getActivity() != null ? getActivity().getSupportFragmentManager() : null;
    }

    public void onFragmentBackPressed() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    protected String getSimpleName(){
        return this.getClass().getSimpleName();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}