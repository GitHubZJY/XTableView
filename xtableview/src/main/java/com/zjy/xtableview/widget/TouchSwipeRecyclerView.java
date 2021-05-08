package com.zjy.xtableview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zjy.xtableview.utils.DensityUtil;
import com.zjy.xtableview.utils.ScrollHelper;
import com.zjy.xtableview.widget.swipe.SwipeRecyclerView;

/**
 * Date: 2020/7/16
 * Author: Yang
 * Describe: 处理侧滑及行列表滚动事件冲突的RecyclerView
 */
public class TouchSwipeRecyclerView extends SwipeRecyclerView {

    private int mDownX;
    private int mDownY;
    private int mMoveOffsetX;
    /**
     * 用来判断是否长按，做对应处理
     */
    private long mTouchTime;
    /**
     * 是否带惯性滚动
     */
    private boolean mIsFilling = true;
    /**
     * 能否侧滑
     */
    private boolean mCanSwipe = true;

    private int mCellWidth;
    private int mHeaderWidth;
    private ScrollHelper mScrollHelper;

    public TouchSwipeRecyclerView(Context context) {
        super(context);
    }

    public TouchSwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchSwipeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void attachScrollHelper(ScrollHelper helper) {
        mScrollHelper = helper;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        boolean isInStart = mScrollHelper.isScrollToStart();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mScrollHelper.stopScrollFilling();
                mDownX = x;
                mDownY = y;
                mTouchTime = System.currentTimeMillis();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int disX = mDownX - x;
                if (isInStart) {
                    //当前位于起点
                    if (mCanSwipe && (disX < 0 || !mOldSwipedLayout.isLeftCompleteClose() || !mOldSwipedLayout.isScrollFinish())) {
                        //右滑，或者菜单还未完全关闭，交由SwipeRecyclerView处理侧滑事件
                        setSwipeItemMenuEnabled(true);
                        return super.onInterceptTouchEvent(e);
                    } else if (isSwipeItemMenuEnabled()) {
                        //左滑并且菜单已经关闭，需要拦截事件，做横向滚动处理
                        mOldSwipedLayout.smoothCloseMenu();
                        //setSwipeItemMenuEnabled(false);
                        return true;
                    }
                } else {
                    //当前未在起点，需要拦截事件，做横向滚动处理
                    mOldSwipedLayout.smoothCloseMenu();
                    //setSwipeItemMenuEnabled(false);
                    return true;
                }

            }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int columnCount = mScrollHelper.getColumnCount();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                mMoveOffsetX = mDownX - x + mScrollHelper.getCurScrollX();
                if (0 > mMoveOffsetX) {
                    mMoveOffsetX = 0;
                } else {
                    //当滑动大于最大宽度时，不在滑动（右边到头了）
                    if ((mMoveOffsetX) > mHeaderWidth + mCellWidth * columnCount - DensityUtil.getScreenWidth(getContext())) {
                        mMoveOffsetX = mHeaderWidth + mCellWidth * columnCount - DensityUtil.getScreenWidth(getContext());
                    }
                }
                mScrollHelper.notifyScroll(mMoveOffsetX);

                break;
            }
            case MotionEvent.ACTION_UP:
                long touchDuration = System.currentTimeMillis() - mTouchTime;
                if (mIsFilling) {
                    if (touchDuration < 500 && Math.abs(mDownY - y) < 100 && Math.abs(mDownX - x) > 20) {
                        mScrollHelper.notifyScrollFilling(mDownX < x);
                    } else {
                        mScrollHelper.notifyScroll(mMoveOffsetX);
                        mScrollHelper.setScrollX(mMoveOffsetX);
                    }
                } else {
                    mScrollHelper.notifyScroll(mMoveOffsetX);
                    mScrollHelper.setScrollX(mMoveOffsetX);
                }


                break;
        }
        return super.onTouchEvent(e);
    }

    public void setHeaderWidth(int headerWidth) {
        mHeaderWidth = headerWidth;
    }

    public void setCellWidth(int cellWidth) {
        mCellWidth = cellWidth;
    }

    /**
     * 设置是否惯性滚动
     *
     * @param flag 是否惯性滚动
     */
    public void enableScrollFilling(boolean flag) {
        mIsFilling = flag;
    }

    /**
     * 设置能否侧滑
     *
     * @param canSwipe 能否侧滑
     */
    public void enableSwipe(boolean canSwipe) {
        mCanSwipe = canSwipe;
    }
}
