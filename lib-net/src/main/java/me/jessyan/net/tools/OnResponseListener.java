package me.jessyan.net.tools;

/**
 * Create by sung at 2020-04-09
 *
 * @desc: 公用接口response回调
 * @notice:
 */
public interface OnResponseListener<T> {

    void onResponseSuccess(int status, T t);

    void onResponseError(int status, Exception e);
}
