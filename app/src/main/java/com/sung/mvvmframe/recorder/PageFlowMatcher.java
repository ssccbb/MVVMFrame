package com.sung.mvvmframe.recorder;

/**
 * Create by sung at 2020/7/31
 *
 * @desc:
 * @notice:
 */
public class PageFlowMatcher {
    /**
     * @desc 打开主页
     */
    public static boolean matchOpenIndex() {
        return PageFlowRecorder.getInstance().vagueMatch(new PageFlow.Builder()
                        .start(PageFlowName.SplashPage)
                        .to(PageFlowName.HomePage)
                        .end());
    }
}
