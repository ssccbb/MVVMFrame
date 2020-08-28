package com.sung.uikit.dialog;

/**
 * Create by sung at 2020-04-03
 *
 * @author sung
 * @desc: 取消回调
 * @notice: 通用弹窗监听
 */
public interface INegitiveClickListener<T extends BaseDialog> {
    void onNegitive(T t);
}
