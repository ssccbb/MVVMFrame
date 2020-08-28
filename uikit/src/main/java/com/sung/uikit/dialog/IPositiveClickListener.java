package com.sung.uikit.dialog;

/**
 * Create by sung at 2020-04-03
 *
 * @author sung
 * @desc: 确定回调
 * @notice: 通用弹窗
 */
public interface IPositiveClickListener<T extends BaseDialog> {
    void onPositive(T t);
}
