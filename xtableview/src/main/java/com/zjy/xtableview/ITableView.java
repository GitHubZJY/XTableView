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

}
