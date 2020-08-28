package com.sung.uikit.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Create by sung at 2020-04-04
 *
 * @desc: dialog基类
 * @notice:
 */
public class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
