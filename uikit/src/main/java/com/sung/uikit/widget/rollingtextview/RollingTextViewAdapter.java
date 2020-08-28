package com.sung.uikit.widget.rollingtextview;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by sung at 2020-04-21
 *
 * @desc:
 * @notice:
 */
public abstract class RollingTextViewAdapter<T> {

    private List<T> data = new ArrayList<>();

    public RollingTextViewAdapter() {
    }

    public RollingTextViewAdapter(List<T> data) {
        this.data = data;
    }

    /**
     * @param index 当前角标
     * @return 待显示的字符串
     */
    final public CharSequence getText(int index) {
        return text(data.get(index));
    }

    protected abstract CharSequence text(T t);

    public int getItemCount() {
        return null == data || data.isEmpty() ? 0 : data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return null == data || data.isEmpty();
    }
}
