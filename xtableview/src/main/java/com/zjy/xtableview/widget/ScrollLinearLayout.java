package com.zjy.xtableview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.zjy.xtableview.utils.ScrollHelper;

/**
 * Date: 2020/7/20
 * Author: Yang
 * Describe: 带惯性滚动的线性布局
 */
public class ScrollLinearLayout extends LinearLayout {

    private Scroller mScroller;
    private ScrollHelper mScrollHelper;
    /**
     * 最大滑动距离
     */
    private int mMaxScrollDistance;
    /**
     * 惯性滑动初始速度 2000像素/s
     */
    private static final int FILLING_START_SPEED = 2000;

    public ScrollLinearLayout(Context context) {
        super(context, null);
    }

    public ScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public void attachScrollHelper(ScrollHelper helper) {
        mScrollHelper = helper;
    }

    /**
     * 触发惯性滑动
     *
     * @param isLeft 惯性方向
     */
    public void scrollFilling(boolean isLeft) {
        if (!mScroller.isFinished()) {
            return;
        }
        int velocityX;
        if (isLeft) {
            velocityX = -FILLING_START_SPEED;
        } else {
            velocityX = FILLING_START_SPEED;
        }
        mScroller.fling(getScrollX(), mScroller.getCurrY(), velocityX, 0, -2000, 3000, 0, 0);
        invalidate();
    }

    /**
     * 强制停止惯性滑动
     */
    public void stopScroll() {
        mScroller.forceFinished(true);
    }

    public void setMaxScrollDistance(int maxDistance) {
        mMaxScrollDistance = maxDistance;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int scrollDistance;
            if (mScroller.getCurrX() < 0) {
                //惯性滚动到最左端临界点
                scrollDistance = 0;
            } else if (mScroller.getCurrX() > mMaxScrollDistance) {
                //惯性滚动到最右端临界点
                mScroller.forceFinished(true);
                scrollDistance = mMaxScrollDistance;
            } else {
                //正常区域内滚动
                scrollDistance = mScroller.getCurrX();
            }
            mScrollHelper.notifyScroll(scrollDistance);
            mScrollHelper.setScrollX(scrollDistance);
            postInvalidate();
        }
        super.computeScroll();
    }
}