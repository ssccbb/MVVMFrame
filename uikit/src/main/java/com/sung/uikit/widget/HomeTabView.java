package com.sung.uikit.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sung.common.bean.HomeTabBean;
import com.sung.uikit.R;

import androidx.annotation.NonNull;

/**
 * Create by sung at 2020-04-06
 *
 * @desc: 单个tab控件
 * @notice: 用于首页tab
 */
public class HomeTabView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private HomeTabBean data;

    private ImageView icon;
    private TextView title;

    private OnTabSelectListener onTabSelectListener;

    public HomeTabView(@NonNull Context context, HomeTabBean tab) {
        super(context);
        this.context = context;
        this.data = tab;
        set();
    }

    public void set() {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_tab_view, null);
        icon = root.findViewById(R.id.iv_tab);
        title = root.findViewById(R.id.tv_tab_name);
        bindData();
        this.addView(root, new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setOnClickListener(this);
    }

    public void setData(HomeTabBean data) {
        this.data = data;
        bindData();
    }

    private void bindData() {
        if (data != null) {
            icon.setImageResource(data.getmTabIcon());
            title.setText(data.getmTabName());
        }
    }

    public void setSelectStatus(boolean selected) {
        this.setSelected(selected);
        this.icon.setSelected(selected);
        this.title.setTextColor(context.getResources().getColor(
                selected ? R.color.theme_tab_selected : R.color.theme_tab_unselect));
    }

    @Override
    public void onClick(View v) {
        if (onTabSelectListener != null) {
            boolean selected = this.isSelected();
            if (selected) {
                return;
            }
            setSelectStatus(true);
            onTabSelectListener.onTabSelect(true);
        }
    }

    public void addOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabSelect(boolean select);
    }
}
