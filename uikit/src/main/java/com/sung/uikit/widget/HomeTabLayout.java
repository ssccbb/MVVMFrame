package com.sung.uikit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sung.common.bean.HomeTabBean;
import com.sung.common.enums.TabTypeEnum;
import com.sung.uikit.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Create by sung at 2020-04-06
 *
 * @desc: 主页tab组
 * @notice: 用于主页底部导航控件
 */
public class HomeTabLayout extends LinearLayout {
    private Context context;

    private List<HomeTabBean> datas = new ArrayList<>();
    private List<HomeTabView> tabs = new ArrayList<>();

    private LayoutParams params;

    private OnTabSelectListener onTabSelectListener;

    public HomeTabLayout(Context context, List<HomeTabBean> datas) {
        super(context);
        this.context = context;
        if (datas != null) {
            this.datas.addAll(datas);
        }
        set();
    }

    public HomeTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        parseAttributes(attrs);
        set();
    }

    private void parseAttributes(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeTabLayout, 0, 0);
        int tabIconsArrayResId = typedArray.getResourceId(R.styleable.HomeTabLayout_tab_icons, R.array.home_tab_icons);
        int tabTitleArrayResId = typedArray.getResourceId(R.styleable.HomeTabLayout_tab_titles, R.array.home_tab_titles);
        String[] titles = context.getResources().getStringArray(tabTitleArrayResId);
        int[] icons = context.getResources().getIntArray(tabIconsArrayResId);
        TabTypeEnum[] types = {TabTypeEnum.HOME_INDEX,
                TabTypeEnum.HOME_QUOTATION,
                TabTypeEnum.HOME_TRANSACTION,
                TabTypeEnum.HOME_CONTRACT,
                TabTypeEnum.HOME_ASSETS};
        if (titles.length == 0 || icons.length == 0 || titles.length != icons.length) {
            return;
        }
        for (int i = 0; i < titles.length; i++) {
            // FIXME: 2020-04-07(add by sung) type目前的对应关系写死 调整xml内排序时请一并调整
            int icon = context.getResources().obtainTypedArray(R.array.home_tab_icons).getResourceId(i, 0);
            int title = context.getResources().obtainTypedArray(R.array.home_tab_titles).getResourceId(i, 0);
            datas.add(new HomeTabBean(types[i], context.getResources().getString(title), icon));
        }
        typedArray.recycle();
    }

    private void set() {
        if (datas == null || datas.size() <= 0) {
            return;
        }
        params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        for (HomeTabBean data : datas) {
            addTabView(data);
        }
    }

    private void addTabView(HomeTabBean tab) {
        HomeTabView tabView = new HomeTabView(context, tab);
        tabView.setSelectStatus(false);
        tabView.addOnTabSelectListener(select -> {
            int position = tabs.indexOf(tabView);
            resetOtherTabSelect(position);
            if (onTabSelectListener != null) {
                onTabSelectListener.onTabSelect(position, tab);
            }
        });
        if (!tabs.contains(tabView)) {
            tabs.add(tabView);
        }
        this.addView(tabView, params);
    }

    /**
     * 重置全部tab选中状态
     */
    public void resetAllTabSelect() {
        try {
            if (tabs == null || tabs.size() <= 0) {
                return;
            }
            for (HomeTabView tab : tabs) {
                tab.setSelectStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置position以外tab选中状态
     */
    public void resetOtherTabSelect(int position) {
        try {
            if (tabs == null || tabs.size() <= 0) {
                return;
            }
            for (int i = 0; i < tabs.size(); i++) {
                if (i == position) {
                    continue;
                }
                tabs.get(i).setSelectStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选中/不选中 tab状态
     */
    public void setSelect(int position, boolean select) {
        try {
            if (tabs == null || tabs.size() <= 0) {
                return;
            }
            resetAllTabSelect();
            tabs.get(position).setSelectStatus(select);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置tabview
     */
    public void resetTabs() {
        this.removeAllViews();
        tabs.clear();
        set();
    }

    /**
     * 添加单个tabview
     */
    public void addTab(HomeTabBean tab) {
        datas.add(tab);
    }

    public List<HomeTabBean> getTabs() {
        return datas;
    }

    public void addOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabSelect(int position, HomeTabBean tab);
    }
}
