package com.zjy.xtableview;

import com.zjy.xtableview.adapter.XTableAdapter;

/**
 * Date: 2020/7/21
 * Author: Yang
 * Describe: 二维列表组件接口
 */
public interface ITableView {

    /**
     * 设置数据填充适配器
     * @param adapter 适配器 {@link XTableAdapter}.
     */
    void setTableAdapter(XTableAdapter<?, ?> adapter);

    /**
     * 设置是否可长按拖动
     * @param enable 是否可拖动
     */
    void setLongPressDragEnable(boolean enable);

    /**
     * 设置是否允许侧滑
     * @param enable
     */
    void setSwipeEnable(boolean enable);

    /**
     * 设置表格监听
     * @param tableListener 监听器 {@link XTableListener}.
     */
    void setTableListener(XTableListener tableListener);

    /**
     * 设置布局反转
     * @param reserveLayout 是否反转
     */
    void setReverseLayout(boolean reserveLayout);

    /**
     * 设置倒序
     * @param stackFromEnd 是否倒序
     */
    void setStackFromEnd(boolean stackFromEnd);

    /**
     * 滑动到指定Item
     * @param position Item下标
     */
    void scrollToPosition(int position);

    /**
     * 滑动到指定Item
     * @param position Item下标
     * @param offset 偏移
     */
    void scrollToPositionWithOffset(int position, int offset);

}
