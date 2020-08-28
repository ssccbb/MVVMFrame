package com.sung.uikit.widget.rollingtextview;

import android.text.TextPaint;
import android.text.TextUtils;

/**
 * Create by sung at 2020-04-21
 *
 * @desc:
 * @notice:
 */
public interface IStrategy {
    RollingTextView.LayoutWithTextSize getLayout(float autoSizeMinTextSizeInPx,
                                                 float autoSizeMaxTextSizeInPx,
                                                 float autoSizeStepGranularityInPx,
                                                 int wantTextSize,
                                                 int width,
                                                 int height,
                                                 TextPaint paint,
                                                 int maxLines,
                                                 CharSequence text,
                                                 TextUtils.TruncateAt mTruncateAt);

    void reset();
}
