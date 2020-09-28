package me.sung.base.bean.response;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice: 根据业务需求调整
 */
public class BaseResponse {
    private String reason;
    private int resultcode;

    public String getMessage() {
        return reason;
    }

    public int getCode() {
        return resultcode;
    }
}
