package com.sung.mvvmframe.adapter;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Create by sung at 2020-04-06
 *
 * @desc: 主页适配器
 * @notice:
 */
public abstract class IndexPagerAdapter<D, T extends Fragment> extends PagerAdapter {
    private final List<D> mDataList;
    // fragmentTag -> ItemPair
    private final Map<String, ItemPair> mItemPairs;
    // mFragmentTags子集，包含所有destroyItem中被detach操作的Fragment tag
    private final Set<String> mDetachedFragmentTagSet;
    // 将要被remove的fragment tag set， 不会被saveState, restore
    private final Set<String> mRemovingFragmentTagSet;
    private final FragmentManager mFragmentManager;

    private Set<String> mTmpIntersectionSet;
    private boolean mNotifyOnChange = true;
    private FragmentTransaction mCurTransaction = null;
    private T mCurrentPrimaryItem = null;

    public IndexPagerAdapter(FragmentManager fm) {
        this(fm, null);
    }

    public IndexPagerAdapter(FragmentManager fm, List<D> dataList) {
        mFragmentManager = fm;
        mItemPairs = new ArrayMap<>();
        mDetachedFragmentTagSet = new HashSet<>();
        mRemovingFragmentTagSet = new HashSet<>();
        if (dataList == null) {
            mDataList = new ArrayList<>();
        } else {
            mDataList = dataList;
        }
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @NonNull
    public abstract T createFragment(int position, D data);

    public void clear() {
        mDataList.clear();
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void remove(int index) {
        mDataList.remove(index);
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void remove(D data) {
        mDataList.remove(data);
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void add(D d) {
        mDataList.add(d);
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(Collection<D> collection) {
        if (collection == null || collection.isEmpty()) {
            return;
        }
        mDataList.addAll(collection);
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    /**
     * return current primary item fragment
     *
     * @return current primary fragment or null
     */
    @Nullable
    public T getCurrentPrimaryItem() {
        return mCurrentPrimaryItem;
    }

    @Nullable
    public D getCurrentPrimaryData() {
        if (mCurrentPrimaryItem == null) {
            return null;
        }

        ItemPair itemPair = mItemPairs.get(mCurrentPrimaryItem.getTag());

        if (itemPair == null) {
            return null;
        }

        int pos = itemPair.position;
        if (pos < 0) {
            pos = 0;
        }

        if (pos >= mItemPairs.size()) {
            return null;
        }

        return getItemData(pos);
    }

    /**
     * 获取position对应的Fragment, can be null
     *
     * @param position position
     * @return Fragment at position or null
     */
    @Nullable
    public T getExistFragment(int position) {
        if (mItemPairs.size() <= 0) {
            return null;
        }
        for (ItemPair itemPair : mItemPairs.values()) {
            if (itemPair.position == position && !TextUtils.isEmpty(itemPair.fragmentTag)) {
                return (T) mFragmentManager.findFragmentByTag(itemPair.fragmentTag);
            }
        }
        return null;
    }

//    public T getExistFragment(D data) {
//        final int index = getDataIndex(data);
//        if (index >= 0) {
//            return getExistFragment(index);
//        }
//        return null;
//    }

    /**
     * 返回所有已经add过的Fragment， 不会为null，不会包含null fragment
     *
     * @return SparseArray position -> T
     */
    @NonNull
    public SparseArray<T> getExistFragments() {
        SparseArray<T> existFragments = new SparseArray<>(mItemPairs.size());

        for (ItemPair itemPair : mItemPairs.values()) {
            if (!TextUtils.isEmpty(itemPair.fragmentTag)) {
                T t = (T) mFragmentManager.findFragmentByTag(itemPair.fragmentTag);
                if (t != null) {
                    existFragments.put(itemPair.position, t);
                }
            }
        }
        return existFragments;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    public CharSequence getPageTitle(int position, D data) {
        return null;
    }

    public D getItemData(int position) {
        return mDataList.get(position);
    }

    protected long getItemId(int position) {
        final D data = getItemData(position);
        return ((long) data.hashCode()) << 32;
//        return (long) position;
    }

    // 该方法是为ViewPager服务，拿到的position 有可能不是index，故标记为过期，避免外界调用
    @Deprecated
    @Override
    public int getItemPosition(Object object) {
        final String tag = ((Fragment) object).getTag();
        final ItemPair itemPair = mItemPairs.get(tag);
        return checkItemPairPosition(itemPair, tag);
    }

    private int checkItemPairPosition(ItemPair itemPair, String tag) {
        if (itemPair == null) {
            mRemovingFragmentTagSet.add(tag);
            return POSITION_NONE;
        }

        final int newPos = getItemIndexInDataList(itemPair);
        if (newPos < 0) {
            mRemovingFragmentTagSet.add(tag);
            return POSITION_NONE;
        } else if (newPos == itemPair.position) {
            return POSITION_UNCHANGED;
        } else {
            itemPair.position = newPos;
            return newPos;
        }
    }

    private int getItemIndexInDataList(@NonNull ItemPair itemPair) {
        for (int i = 0, size = getCount(); i < size; i++) {
            if (getItemId(i) == itemPair.itemId) {
                return i;
            }
        }
        return -1;
    }

    public List<D> getData() {
        return mDataList;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        if (container.getId() == -1) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @Override
    @NotNull
    public Object instantiateItem(ViewGroup container, int position) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }

        final long itemId = this.getItemId(position);
        // Do we already have this fragment?
        final String name = makeFragmentName(container.getId(), itemId);
        mDetachedFragmentTagSet.remove(name);
        mRemovingFragmentTagSet.remove(name);

        Fragment fragment = this.mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            this.mCurTransaction.attach(fragment);
        } else {
            fragment = createFragment(position, getItemData(position));
            this.mCurTransaction.add(container.getId(), fragment, name);
        }
        if (fragment != this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        addItemPair(position, itemId, name);
        return fragment;
    }

    private void addItemPair(int position, long itemId, String name) {
        ItemPair itemPair = mItemPairs.get(name);
        if (itemPair == null) {
            itemPair = new ItemPair(itemId, name);
            itemPair.position = position;
            mItemPairs.put(name, itemPair);
        } else {
            itemPair.position = position;
        }
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }

        final String tag = ((Fragment) object).getTag();
        final boolean isRemoveItem = mRemovingFragmentTagSet.remove(tag);
        if (isRemoveItem) {
            mCurTransaction.remove((Fragment) object);
            mItemPairs.remove(tag);
        } else {
            this.mCurTransaction.detach((Fragment) object);
            mDetachedFragmentTagSet.add(tag);
        }
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        T fragment = (T) object;
        if (fragment != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        removeDetachedScrapFragment();

        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }
    }

    // 移除已经detach的removing fragment
    private void removeDetachedScrapFragment() {
        if (!mRemovingFragmentTagSet.isEmpty() && !mDetachedFragmentTagSet.isEmpty()) {
            if (mTmpIntersectionSet == null) {
                mTmpIntersectionSet = new HashSet<>(mRemovingFragmentTagSet);
            } else {
                mTmpIntersectionSet.addAll(mRemovingFragmentTagSet);
            }
            final Set<String> intersection = mTmpIntersectionSet;
            intersection.retainAll(mDetachedFragmentTagSet);
            if (!intersection.isEmpty()) {
                // 有要remove的detached fragment
                boolean commit = false;
                for (String tag : intersection) {
                    Fragment fragment = mFragmentManager.findFragmentByTag(tag);
                    if (fragment != null) {
                        if (mCurTransaction == null) {
                            mCurTransaction = mFragmentManager.beginTransaction();
                        }
                        mCurTransaction.remove(fragment);
                        commit = true;
                    }
                }

                if (commit && mCurTransaction != null) {
                    mCurTransaction.commitNowAllowingStateLoss();
                    mCurTransaction = null;
                }

                mRemovingFragmentTagSet.removeAll(intersection);
                mDetachedFragmentTagSet.removeAll(intersection);
                for (String tag : intersection) {
                    mItemPairs.remove(tag);
                }

                intersection.clear();
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (!mDetachedFragmentTagSet.isEmpty()) {
            for (String tag : mDetachedFragmentTagSet) {
                checkItemPairPosition(mItemPairs.get(tag), tag);
            }
        }
        super.notifyDataSetChanged();
    }

    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getPageTitle(position, getItemData(position));
    }

    @Override
    public Parcelable saveState() {
        Bundle state = new Bundle();
        if (!mItemPairs.isEmpty()) {
            state.putParcelableArray("ips", mItemPairs.values().toArray(new ItemPair[mItemPairs.size()]));
        }
        if (!mDetachedFragmentTagSet.isEmpty()) {
            state.putStringArray("dfts", mDetachedFragmentTagSet.toArray(new String[mDetachedFragmentTagSet.size()]));
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);

            if (bundle.containsKey("dfts")) {
                final String[] arrDestroyTags = bundle.getStringArray("dfts");
                if (arrDestroyTags != null && arrDestroyTags.length > 0) {
                    Collections.addAll(mDetachedFragmentTagSet, arrDestroyTags);
                }
            }

            if (bundle.containsKey("ips")) {
                final Parcelable[] parcelables = bundle.getParcelableArray("ips");
                if (parcelables != null && parcelables.length > 0) {
                    for (Parcelable parcelable : parcelables) {
                        if (parcelable instanceof ItemPair) {
                            ItemPair itemPair = (ItemPair) parcelable;
                            mItemPairs.put(itemPair.fragmentTag, itemPair);
//                            mDetachedFragmentTagSet.add(itemPair.fragmentTag);
                        }
                    }
                }
            }
        }
    }

    private static class ItemPair implements Parcelable {
        public static final Creator<ItemPair> CREATOR = new Creator<ItemPair>() {
            @Override
            public ItemPair createFromParcel(Parcel in) {
                return new ItemPair(in);
            }

            @Override
            public ItemPair[] newArray(int size) {
                return new ItemPair[size];
            }
        };
        final long itemId; // 不可变：唯一标示item对应的data
        final String fragmentTag; // 不可变：唯一标示item对应的fragment
        int position; // 可变：纪录当前fragment在viewpager中的位置

        ItemPair(long itemId, String fragmentTag) {
            this.itemId = itemId;
            this.fragmentTag = fragmentTag;
        }

        ItemPair(Parcel in) {
            itemId = in.readLong();
            fragmentTag = in.readString();
            position = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(itemId);
            dest.writeString(fragmentTag);
            dest.writeInt(position);
        }
    }
}
