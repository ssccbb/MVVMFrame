package me.sung.uikit.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sung.uikit.R;

/**
 * Create by sung at 2020-04-08
 *
 * @desc: 内容加载器
 * @notice: 1、内容展示状态
 *          2、加载中状态
 *          3、加载失败状态
 *          4、空白状态
 */
public class ContentLoader extends FrameLayout {
    private OnLoadContentCallback mReloadCallback;
    private Context mContext;

    /**
     * 当前可见view
     */
    private View mCurVisView;
    /**
     * error
     */
    private TextView mErrorTips;
    private TextView mReload;

    private View mLoadingView, mEmptyView, mErrorView, mContentView;

    public ContentLoader(Context context) {
        super(context);
        this.mContext = context;
        set();
    }

    public ContentLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        set();
    }

    public ContentLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        set();
    }

    public void attachCallback(OnLoadContentCallback callback) {
        this.mReloadCallback = callback;
    }

    private void set() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.layout_content_loader, null);
        mLoadingView = root.findViewById(R.id.fl_loading_view);
        mEmptyView = root.findViewById(R.id.fl_empty_view);
        mErrorView = root.findViewById(R.id.fl_error_view);
        mContentView = root.findViewById(R.id.fl_content_view);
        findview();
        this.addView(root, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void findview() {
        mErrorTips = mErrorView.findViewById(R.id.tv_error_tips);
        mReload = mErrorView.findViewById(R.id.tv_reload);

        mReload.setOnClickListener(v -> {
            if (mReloadCallback != null) {
                mReloadCallback.onReload();
            }
        });
    }

    private void resetErrorTips(String tips) {
        mErrorTips.setText(TextUtils.isEmpty(tips)
                ? mContext.getResources().getString(R.string.app_loading_error) : tips);
    }

    /**
     * 展示内容
     */
    public void showContent() {
        if (mCurVisView != null) {
            mCurVisView.setVisibility(GONE);
        }
        mCurVisView = mContentView;
        mCurVisView.setVisibility(VISIBLE);
        mCurVisView.bringToFront();
        setVisibility(GONE);
    }

    /**
     * 展示空
     */
    public void showEmpty() {
        if (mCurVisView != null) {
            mCurVisView.setVisibility(GONE);
        }
        mCurVisView = mEmptyView;
        mCurVisView.setVisibility(VISIBLE);
        mCurVisView.bringToFront();
        setVisibility(VISIBLE);
    }

    /**
     * 展示错误
     */
    public void showError(String tips) {
        if (mCurVisView != null) {
            mCurVisView.setVisibility(GONE);
        }
        mCurVisView = mErrorView;
        resetErrorTips(tips);
        mCurVisView.setVisibility(VISIBLE);
        mCurVisView.bringToFront();
        setVisibility(VISIBLE);
    }

    /**
     * 展示加载中
     */
    public void showLoading() {
        if (mCurVisView != null) {
            mCurVisView.setVisibility(GONE);
        }
        mCurVisView = mLoadingView;
        mCurVisView.setVisibility(VISIBLE);
        mCurVisView.bringToFront();
        setVisibility(VISIBLE);
    }

    public interface OnLoadContentCallback {
        void onReload();
    }
}
