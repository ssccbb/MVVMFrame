package com.sung.uikit.dialog;

import android.content.Context;
import android.view.Window;

/**
 * Create by sung at 2020-04-03
 *
 * @desc: 通用自定义弹窗
 * @notice: 展示元素通过builder创建
 */
public class CustomDialog extends BaseDialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE | Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
