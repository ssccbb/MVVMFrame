package com.sung.common.manager;

/**
 * Create by sung at 2020-04-03
 *
 * @desc: 国际化manager
 * @notice: 处理动态语言切换相关
 */
public class MultiLangManager {
    /* 多语言手动切换状态 */
    private boolean mOpenMultiLan = false;

    private static class Holder {
        private static MultiLangManager holder = new MultiLangManager();
    }

    public static MultiLangManager getInstance() {
        return Holder.holder;
    }

    public boolean isOpenMultiLanguage() {
        return mOpenMultiLan;
    }
}
