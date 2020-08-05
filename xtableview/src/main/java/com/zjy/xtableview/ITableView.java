package com.zjy.xtableview;

import com.zjy.xtableview.adapter.XTableAdapter;
import com.zjy.xtableview.model.TableRowModel;

import java.util.List;

/**
 * Date: 2020/7/21
 * Author: Yang
 * Describe: 二维列表组件接口
 */
public interface ITableView {

    /**
     * 绑定表格数据
     * @param headerModel 表头数据
     * @param dataList 表行数据
     */
    <T, H> void bindData(String title, List<T> headerModel, List<H> dataList);

    /**
     * 更新某一行数据
     * @param position 行下标
     * @param data 更新的内容
     */
    <T extends TableRowModel<?,?>> void notifyItemData(int position, T data);

    /**
     * 设置数据填充适配器
     * @param adapter 适配器 {@link XTableAdapter}.
     */
    void setTableAdapter(XTableAdapter<?,?> adapter);

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
