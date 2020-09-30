package me.sung.base.bean.response;

/**
 * Create by sung at 2020/9/29
 *
 * @desc: 登陆接口返回值
 * @notice: 服务端接口会返回多个字段本地并非全用得上，为缓解程序数据传递压力#LoginResponse仅用于接口数据获取
 *          实际使用需将其转换为#UserModel.parserTo()
 */
public class LoginResponse extends BaseResponse {
    public String account;
    public String token;
    public long uid;
}
