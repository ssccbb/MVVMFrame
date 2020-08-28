package com.sung.uikit;

import android.content.Context;

import com.sung.uikit.dialog.CustomDialog;
import com.sung.uikit.dialog.DialogBuilder;
import com.sung.uikit.dialog.IPositiveClickListener;

/**
 * Create by sung at 2020/8/28
 *
 * @desc: 弹窗管理类
 * @notice:
 */
public class DialogManager {

    /**
     * 展示简单文案提示弹窗
     *
     * @param context
     * @param tips     文案
     * @param listener
     * @return dialog
     * @author sung at 2020-04-04
     * @notice 无点击监听
     */
    public static CustomDialog openSimpleTipDialog(Context context, String tips, IPositiveClickListener listener) {
        return new DialogBuilder(context)
                .titleGone()
                .desc(tips)
                .positive(context.getResources().getString(R.string.dialog_know))
                .addPositiveListener(listener)
                .negitiveGone()
                .creat();
    }
}
