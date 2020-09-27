package me.sung.uikit.widget;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.sung.base.bean.config.MLoaderConfig;
import com.sung.uikit.R;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

/**
 * Create by sung at 2020-05-22
 *
 * @desc: 加载器
 * @notice:
 */
public class MLoader extends DialogFragment {
    private ProgressBar mProgress;
    private TextView mLoading;

    private @ColorRes
    int mDefaultProgressColor;
    private @ColorRes
    int mDefaultTipsColor;
    private String mDefaultTips;
    private boolean mDefaultTipsVisible;

    private MLoaderConfig mLoaderConfig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FadeDialogFragmentStyle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_m_loading, null, false);

        mDefaultProgressColor = R.color.theme_color;
        mDefaultTipsColor = android.R.color.black;
        mDefaultTips = getString(R.string.app_loading_tips);
        mDefaultTipsVisible = true;

        mLoaderConfig = (MLoaderConfig) getArguments().getSerializable(MLoader.class.getSimpleName());
        mProgress = view.findViewById(R.id.pb_loading);
        mLoading = view.findViewById(R.id.tv_loading);

        if (mLoaderConfig != null) {
            mLoading.setVisibility(mLoaderConfig.isTipsVisible() ? View.VISIBLE : View.GONE);
            mLoading.setText(TextUtils.isEmpty(mLoaderConfig.getTips()) ? mDefaultTips : mLoaderConfig.getTips());
            mLoading.setTextColor(getResources().getColor(mLoaderConfig.getTipsColor() != 0 ? mLoaderConfig.getTipsColor() : mDefaultTipsColor));
        }
        return view;
    }

    public static MLoader show(Context context) {
        return show(context, new MLoaderConfig.Builder().setTipsVisible(false).creat());
    }

    public static MLoader show(Context context, String tips) {
        return show(context, new MLoaderConfig.Builder().setTipsVisible(true).setTips(tips).creat());
    }

    public static MLoader show(Context context, boolean tipsVisible) {
        return show(context, new MLoaderConfig.Builder().setTipsVisible(tipsVisible).creat());
    }

    public static MLoader show(Context context, MLoaderConfig config) {
        MLoader dialog = new MLoader();
        Bundle b = new Bundle();
        b.putSerializable(MLoader.class.getSimpleName(), config);
        dialog.setArguments(b);
        if (context != null) {
            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(dialog, MLoader.class.getSimpleName());
            ft.commitAllowingStateLoss();
        }
        return dialog;
    }

    public static void hide(MLoader loading){
        loading.dismissAllowingStateLoss();
    }
}
