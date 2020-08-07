package com.zjy.xtableview.utils;

import android.util.SparseArray;

import com.zjy.xtableview.widget.TableItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/7/16
 * Author: Yang
 * Describe: 多个横向列表同步滑动处理
 */
public class ScrollHelper {

    private List<TableItemView.ItemScrollListener> scrollListeners;
    private SparseArray<TableItemView> itemViewsMap;
    private int mScrollX;
    private int mColumnCount;

    public ScrollHelper() {
        scrollListeners = new ArrayList<>();
        itemViewsMap = new SparseArray<>();
    }

    public void addListener(TableItemView.ItemScrollListener listener) {
        if (scrollListeners == null || scrollListeners.contains(listener)) {
            return;
        }
        scrollListeners.add(listener);
    }

    public void bindTableItemView(int pos, TableItemView itemView) {
        if (itemViewsMap.get(pos) == null) {
            itemView.notifyScroll(mScrollX);
        }
        itemViewsMap.put(pos, itemView);
    }

    public void removeListener(TableItemView.ItemScrollListener listener) {
        if (scrollListeners == null || !scrollListeners.contains(listener)) {
            return;
        }
        scrollListeners.remove(listener);
    }


    public void notifyScroll(int x) {
        for (int i = 0; i < itemViewsMap.size(); i++) {
            itemViewsMap.get(itemViewsMap.keyAt(i)).notifyScroll(x);
        }
        for (TableItemView.ItemScrollListener listener : scrollListeners) {
            listener.scroll(x);
        }
    }

    public void notifyScrollFilling(boolean isLeft) {
        for (int i = 0; i < itemViewsMap.size(); i++) {
            itemViewsMap.get(itemViewsMap.keyAt(i)).notifyScrollFilling(isLeft);
        }
    }

    public void stopScrollFilling() {
        for (int i = 0; i < itemViewsMap.size(); i++) {
            itemViewsMap.get(itemViewsMap.keyAt(i)).stopScroll();
        }
    }

    public void setScrollX(int x) {
        mScrollX = x;
    }

    public int getCurScrollX() {
        return mScrollX;
    }

    public boolean isScrollToStart() {
        return mScrollX == 0;
    }

    public void setColumnCount(int count) {
        mColumnCount = count;
    }

    public int getColumnCount() {
        return mColumnCount;
    }

    public void destroy() {
        if (scrollListeners != null) {
            scrollListeners.clear();
        }
        if (itemViewsMap != null) {
            itemViewsMap.clear();
        }
        mScrollX = 0;
        mColumnCount = 0;
    }
}
