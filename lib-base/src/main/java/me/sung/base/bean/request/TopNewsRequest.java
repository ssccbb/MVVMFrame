package me.sung.base.bean.request;

/**
 * Create by sung at 2020/9/28
 *
 * @desc:
 * @notice:
 */
public class TopNewsRequest extends BaseRequest {
    private String key;
    private String type;

    public TopNewsRequest(String key, String type) {
        this.key = key;
        this.type = type;
    }
}
