package com.sung.mvvmframe.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sung.mvvmframe.recorder.PageFlowRecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    protected Bundle mBundleData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutId() != -1 ? inflater.inflate(getLayoutId(), container, false)
                : super.onCreateView(inflater, container, savedInstanceState);
        if (view != null) {
            unbinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mBundleData = getArguments();
        }
        init();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            PageFlowRecorder.getInstance().addPageAuto(this.getClass().getSimpleName());
        }
    }

    protected int getLayoutId() {
        return -1;
    }

    protected void init() {

    }

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