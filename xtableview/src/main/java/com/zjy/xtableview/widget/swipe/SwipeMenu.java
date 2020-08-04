package com.zjy.xtableview.widget.swipe;


import android.widget.LinearLayout;

import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class SwipeMenu {

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {}

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private SwipeMenuLayout mMenuLayout;
    private int mOrientation;
    private List<SwipeMenuItem> mSwipeMenuItems;

    public SwipeMenu(SwipeMenuLayout menuLayout) {
        this.mMenuLayout = menuLayout;
        this.mOrientation = SwipeMenu.HORIZONTAL;
        this.mSwipeMenuItems = new ArrayList<>(2);
    }

    /**
     * Set a percentage.
     *
     * @param openPercent such as 0.5F.
     */
    public void setOpenPercent(@FloatRange(from = 0.1, to = 1) float openPercent) {
        mMenuLayout.setOpenPercent(openPercent);
    }

    /**
     * The duration of the set.
     *
     * @param scrollerDuration such 500.
     */
    public void setScrollerDuration(@IntRange(from = 1) int scrollerDuration) {
        mMenuLayout.setScrollerDuration(scrollerDuration);
    }

    /**
     * Set the menu mOrientation.
     *
     * @param orientation use {@link SwipeMenu#HORIZONTAL} or {@link SwipeMenu#VERTICAL}.
     *
     * @see SwipeMenu#HORIZONTAL
     * @see SwipeMenu#VERTICAL
     */
    public void setOrientation(@OrientationMode int orientation) {
        this.mOrientation = orientation;
    }

    /**
     * Get the menu mOrientation.
     *
     * @return {@link SwipeMenu#HORIZONTAL} or {@link SwipeMenu#VERTICAL}.
     */
    @OrientationMode
    public int getOrientation() {
        return mOrientation;
    }

    public void addMenuItem(SwipeMenuItem item) {
        mSwipeMenuItems.add(item);
    }

    public void removeMenuItem(SwipeMenuItem item) {
        mSwipeMenuItems.remove(item);
    }

    public List<SwipeMenuItem> getMenuItems() {
        return mSwipeMenuItems;
    }

    public boolean hasMenuItems() {
        return !mSwipeMenuItems.isEmpty();
    }
}