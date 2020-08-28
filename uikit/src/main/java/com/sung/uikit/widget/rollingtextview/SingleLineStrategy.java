package com.sung.uikit.widget.rollingtextview;

import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

/**
 * Create by sung at 2020-04-21
 *
 * @desc:
 * @notice:
 */
public class SingleLineStrategy implements IStrategy {
    @Override
    public RollingTextView.LayoutWithTextSize getLayout(float autoSizeMinTextSizeInPx,
                                                        float autoSizeMaxTextSizeInPx,
                                                        float autoSizeStepGranularityInPx,
                                                        int wantTextSize,
                                                        int width,
                                                        int height,
                                                        TextPaint paint,
                                                        int maxLines,
                                                        CharSequence text,
                                                        TextUtils.TruncateAt truncateAt) {

        BoringLayout.Metrics metrics = BoringLayout.isBoring(text, paint);

        Layout layout;

        if (null == metrics) {
            layout = StaticLayoutHelper.createStaticLayout(
                    text,
                    paint,
                    width,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false,
                    truncateAt,
                    width,
                    1);
        } else {
            layout = new BoringLayout(
                    text,
                    paint,
                    width,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    metrics,
                    false,
                    truncateAt,
                    width);
        }

        RollingTextView.LayoutWithTextSize lt = new RollingTextView.LayoutWithTextSize();
        lt.layout = layout;
        lt.textSize = wantTextSize;
        return lt;
    }

    @Override
    public void reset() {

    }
}