package com.sung.mvvmframe.recorder;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Create by sung at 2020/7/30
 *
 * @desc: 交互流程 Builder
 * @notice:
 */
public class PageFlow {
    /**
     * 需要匹配的操作页面流
     *
     * @notice 建议最多不超过4个页面
     */
    private static LinkedList<String> mOperationFlow;

    public static class Builder {

        public Builder() {
            if (mOperationFlow == null) {
                mOperationFlow = new LinkedList<>();
            }
            mOperationFlow.clear();
        }

        // 指定类型页面
        public Builder start(@NotNull PageFlowName page) {
            return add(page.getValue());
        }

        public Builder to(PageFlowName page) {
            return add(page.getValue());
        }

        // 未指定类型页面
        public Builder add(String page){
            if (TextUtils.isEmpty(page)){
                return this;
            }
            mOperationFlow.add(page);
            return this;
        }

        public LinkedList<String> end() {
            return mOperationFlow;
        }
    }
}
