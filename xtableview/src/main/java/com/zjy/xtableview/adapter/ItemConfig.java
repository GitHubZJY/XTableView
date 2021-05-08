package com.zjy.xtableview.adapter;

/**
 * Date: 2021/3/17
 * Author: Yang
 * Describe: 适配器相关配置包装
 */
public class ItemConfig {

    //是否显示刷新动画
    private boolean isNotifyAnim;
    //每一行头部的宽度
    private int headerWidth;
    //每一个普通单元格的宽度
    private int cellWidth;
    //每一行的高度
    private int rowHeight;

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public int getHeaderWidth() {
        return headerWidth;
    }

    public void setHeaderWidth(int headerWidth) {
        this.headerWidth = headerWidth;
    }

    public boolean isNotifyAnim() {
        return isNotifyAnim;
    }

    public void setNotifyAnim(boolean notifyAnim) {
        isNotifyAnim = notifyAnim;
    }
}
