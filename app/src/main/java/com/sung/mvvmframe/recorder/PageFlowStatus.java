package com.sung.mvvmframe.recorder;

/**
 * Create by sung at 2020/8/6
 *
 * @desc: 页面活动状态
 * @notice: 除Activity与Fragment内非手动添加页面
 */
public enum PageFlowStatus {
    Default(-1), Alive(0), Destory(1);

    private int type;

    PageFlowStatus(int type) {
        this.type = type;
    }

    public static PageFlowStatus nameOfStatus(String name){
        for (PageFlowStatus value : values()) {
            if (value.name().equals(name)){
                return value;
            }
        }
        return Default;
    }
}
