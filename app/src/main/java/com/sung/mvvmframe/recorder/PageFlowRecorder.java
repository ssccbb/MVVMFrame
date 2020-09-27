package com.sung.mvvmframe.recorder;

import android.text.TextUtils;
import android.util.Log;

import me.sung.base.utils.JsonParser;
import com.sung.mvvmframe.BuildConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Create by sung at 2020/7/30
 *
 * @desc: 用户操作页面轨迹记录
 * @notice: 用于判断操作流
 */
public class PageFlowRecorder {
    /** 页面记录*/
    private LinkedList<Map<String, String>> mPageTrack = new LinkedList<>();
    /** 限制数据存储长度*/
    private int mLimitSize = 15;

    private static final String KEY_PAGE_NAME = "name";
    private static final String KEY_PAGE_TIME = "time";
    private static final String KEY_PAGE_STATUS = "status";

    private PageFlowRecorder() {
        mPageTrack.clear();
    }

    private static class Holder {
        public static PageFlowRecorder recorder = new PageFlowRecorder();
    }

    public static PageFlowRecorder getInstance() {
        return Holder.recorder;
    }

    /**
     * 添加页面访问记录
     *
     * @desc 记录Activity以及Fragment区别于addPageHand()方法
     * @param pageName 定义页面名称（传参的话直接使用class名使用判断的话通过PageFlowName规范）
     */
    public synchronized void addPageAuto(String pageName) {
        addPage(pageName, PageFlowStatus.Alive);
    }

    /**
     * 不在对应页面内添加页面访问记录
     *
     * @desc 仅针对统计不到的Activity&Fragment的页面添加访问记录 多数为Dialog
     * @notice 调用于所展示的页面外部方法（例如：Dialog实际展示的方法内手动添加对应Dialog访问记录）
     * @param page 定义页面名称
     */
    public synchronized void addPageHand(String page) {
        addPage(page, PageFlowStatus.Default);
    }

    /**
     * 添加页面访问记录
     *
     * @param pageName 页面名称
     * @param status 页面状态
     */
    private void addPage(String pageName, PageFlowStatus status){
        if (TextUtils.isEmpty(pageName)) {
            return;
        }
        if (mPageTrack != null && !mPageTrack.isEmpty() && getCurrentPage().equals(pageName)) {
            //页面重新展示
            mPageTrack.get(mPageTrack.size() - 1).remove(KEY_PAGE_STATUS);
            mPageTrack.get(mPageTrack.size() - 1).put(KEY_PAGE_STATUS, status.name());
            return;
        }
        if (mPageTrack.size() >= mLimitSize) {
            try {
                //移除超量元素(旧记录第一条)
                mPageTrack.remove(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, String> page = new HashMap<>();
        page.put(KEY_PAGE_NAME, pageName);
        page.put(KEY_PAGE_TIME, String.valueOf(System.currentTimeMillis()));
        page.put(KEY_PAGE_STATUS, status.name());
        mPageTrack.add(page);

        _log();
    }

    /**
     * 页面销毁更新状态
     *
     * @desc 查找传入展示页面并更新页面VISIBLE状态
     * @notice 只Activity&Fragment会有存活与销毁状态
     * @param page 定义页面名称
     */
    public synchronized void updatePage(String page) {
        if (TextUtils.isEmpty(page) || mPageTrack == null || mPageTrack.isEmpty()) {
            return;
        }
        for (Map<String, String> map : mPageTrack) {
            boolean match = map.get(KEY_PAGE_NAME).equals(page);
            if (match) {
                if (map.containsKey(KEY_PAGE_STATUS)) {
                    map.remove(KEY_PAGE_STATUS);
                }
                map.put(KEY_PAGE_STATUS, PageFlowStatus.Destory.name());
            }
        }
    }

    /**
     * 模糊匹配flow
     *
     * @param pageFlow 定义操作流
     * @desc 匹配模糊定义操作流 操作流程中允许有多余操作
     * @eg 定义：主页->私聊->VIP开通  实际：主页->私聊->个人信息->返回私聊->开通vip
     * @return boolean
     */
    public boolean vagueMatch(LinkedList<String> pageFlow) {
        try {
            //记录只存mLimitSize个页面以内
            int recorderFlowSize = mPageTrack.size();
            int pageFlowSize = pageFlow.size();

            LinkedList<Integer> positions = new LinkedList<>();
            //定义flow为有序的
            //倒序查找flow内各个页面在记录中最近的位置(定义flow不用考虑重复问题)
            //小循环驱动大循环缩短循环耗时
            for (int flowSize = (pageFlowSize - 1); flowSize >= 0; flowSize--) {
                //页面
                String page = pageFlow.get(flowSize);
                //倒序查找page在记录中的位置
                //因为记录中记得可能会出现跨间隔重复 此处取最后一次展示该页面的位置
                for (int size = (recorderFlowSize - 1); size >= 0; size--) {
                    Map<String, String> map = mPageTrack.get(size);
                    if (map.get(KEY_PAGE_NAME).equals(page)) {
//                        if (map.get(KEY_PAGE_STATUS).equals(PageFlowStatus.Destory)){
//                            定义的操作流不涉及到过多层页面跳转且页面关联性强在操作流中必然存活
//                            return false;
//                        }
                        positions.add(size);
                        break;
                    }
                }
            }
            if (positions.size() != pageFlowSize) {
                return false;
            }
            //判断位置集合符不符合操作顺序先后
            int old = -1;
            boolean match = true;
            for (Integer pos : positions) {
                //页面不是降序
                if (old != -1 && pos >= old) {
                    return false;
                }
                //定义页面流操作连续位 间隔5个页面以上
                //例：ABC跳转顺序 BC之间有多余5个页面以上的跳转
                if (old != -1 && Math.abs(pos - old) > 5) {
                    return false;
                }
                old = pos;
            }
            return match;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 精准匹配flow
     *
     * @param pageFlow 定义操作流
     * @desc 精确匹配操作流 中途不允许存在多余操作
     * @return boolean
     */
    public boolean accurateMatch(LinkedList<String> pageFlow) {
        try {
            String lastPage = pageFlow.get(pageFlow.size() - 1);
            int position = -1;
            for (int pos = (mPageTrack.size() - 1); pos >= 0; pos--) {
                if (mPageTrack.get(pos).get(KEY_PAGE_NAME).equals(lastPage)) {
                    //操作流结束页面在实际记录中最近的位置（记录中多次出现只算最新的一次访问）
                    position = pos;
                    break;
                }
            }
            if (position == -1) {
                return false;
            }
            //position位置往前 依次对比定义的操作流以及记录position位置前推pageFlow.size()位
            int diff = position - (pageFlow.size() - 1);
            for (int pos = (pageFlow.size() - 1); pos >= 0; pos--) {
                String flowPage = pageFlow.get(pos);
                String recorderPage = mPageTrack.get(pos + diff).get(KEY_PAGE_NAME);
                if (!flowPage.equals(recorderPage)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 该页面最近是否有操作记录
     *
     * @desc 最近操作定义为 A页跳转B页又回到A页操作
     * @param page 定义的页面名
     * @return boolean
     */
    public boolean isRecentPage(String page) {
        try {
            //上上页为当前页
            int size = mPageTrack.size();
            if (getPageName(size - 1 - 2).equals(page)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取所有记录的访问页面名集合
     *
     * @return LinkedList
     */
    private LinkedList<String> getPageTrack() {
        LinkedList<String> list = new LinkedList();
        for (Map<String, String> map : mPageTrack) {
            list.add(map.get(KEY_PAGE_NAME));
        }
        return list;
    }

    /**
     *  获取访问页面状态
     *
     * @desc 查询指定页面的页面状态（在页面访问记录内最新的一条）
     * @notice Default(手动添加页面流程无存活状态), Alive(页面存活), Destory(页面销毁);
     * @param page 查询页面
     * @return PageFlowStatus
     */
    public PageFlowStatus getPageStatus(String page){
        Map<String, String> pageMap = getPageMap(page);
        if (pageMap!= null && pageMap.containsKey(KEY_PAGE_STATUS)){
            return PageFlowStatus.nameOfStatus(pageMap.get(KEY_PAGE_STATUS));
        }
        return PageFlowStatus.Default;
    }

    /**
     * 查询最近一次的页面出现记录
     *
     * @desc 倒序查询 仅查询最近一次
     * @param page
     * @return 页面记录
     */
    private Map<String, String> getPageMap(String page){
        int size = mPageTrack.size() - 1;
        for (int i = size; i >= 0; i--) {
            String name = mPageTrack.get(i).get(KEY_PAGE_NAME);
            if (name.equals(page)){
                return mPageTrack.get(i);
            }
        }
        return null;
    }

    /**
     * 获取页面名称
     *
     * @param position 页面访问位置
     * @return 页面名称
     */
    private String getPageName(int position) {
        try {
            return mPageTrack.get(position).get(KEY_PAGE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前停留页
     *
     * @return 页面名称
     */
    public String getCurrentPage() {
        return getPageName(mPageTrack.size() - 1);
    }

    private void _log() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (mPageTrack == null || mPageTrack.isEmpty()) {
            return;
        }
        Log.d(PageFlowRecorder.class.getSimpleName(), "-----------------------");
        for (Map<String, String> map : mPageTrack) {
            Log.d(PageFlowRecorder.class.getSimpleName(), JsonParser.gson.toJson(map));
        }
        Log.d(PageFlowRecorder.class.getSimpleName(), "-----------------------");
    }
}
