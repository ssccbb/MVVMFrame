package me.sung.uikit.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by sung at 2020-04-09
 *
 * @desc: 最大化数据展示的recyclerview
 * @notice: 展示全部数据不支持滑动
 */
public class MaxRecyclerview extends RecyclerView {

    public MaxRecyclerview(Context context) {
        super(context);
    }

    public MaxRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
