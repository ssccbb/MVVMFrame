package com.sung.uikit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Create by sung at 2018/12/7
 *
 * @Description: 可拖动松手回原坐标的SimpleDraweeView
 * @notice 可拖动范围取决于父容器
 */
public class DropSimpleDraweeView extends SimpleDraweeView {
    private float default_x = -1;
    private float default_y = -1;
    private float down_x;
    private float down_y;

    public DropSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public DropSimpleDraweeView(Context context) {
        super(context);
    }

    public DropSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropSimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DropSimpleDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_x = event.getX();
                down_y = event.getY();
                if (default_x == -1 || default_y == -1) {
                    default_x = getX();
                    default_y = getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - down_x));
                setTranslationY(getY() + (event.getY() - down_y));
                break;
            case MotionEvent.ACTION_UP:
                setTranslationX(default_x);
                setTranslationY(default_y);
                default_x = -1;
                default_y = -1;
                break;
        }
        return true;
    }
}
