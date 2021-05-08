package com.zjy.xtableview;

/**
 * Date: 2020/8/7
 * Author: Yang
 * Describe: 二维表格布局监听
 */
public interface XTableListener {

    /**
     * 侧滑菜单点击
     * @param position 对应行下标
     */
    void clickSwipeMenu(int position);

    /**
     * 拖拽变换
     * @param fromPos 原Item的下标
     * @param toPos 目标Item的下标
     */
    void onItemMove(int fromPos, int toPos);

    /**
     * 点击每一列的头部
     * @param position 当前列的下标
     */
    void onColumnHeaderItemClick(int position);

}
