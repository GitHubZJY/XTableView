package com.zjy.xtableview;

import com.zjy.xtableview.model.TableHeaderModel;
import com.zjy.xtableview.model.TableItemModel;
import com.zjy.xtableview.widget.item.TableCellAdapter;

import java.util.List;

/**
 * Date: 2020/7/21
 * Author: Yang
 * Describe: 二维列表组件接口
 */
public interface ITableView {

    /**
     * 绑定表格数据
     * @param headerModel 表头数据 {@link TableHeaderModel}.
     * @param dataList 表行数据 {@link TableItemModel}.
     */
    void bindData(TableHeaderModel headerModel, List<TableItemModel> dataList);

    /**
     * 更新某一行数据
     * @param position 行下标
     * @param data 更新的内容 {@link TableItemModel}.
     */
    void notifyItemData(int position, TableItemModel data);

    /**
     * 设置单元格样式适配器
     * @param cellAdapter 适配器 {@link TableCellAdapter}.
     */
    void setCellAdapter(TableCellAdapter cellAdapter);

    /**
     * 设置单元格点击监听，需要在bindData绑定数据之后调用
     * @param listener 监听对象 {@link TableItemClickListener}.
     */
    void setTableItemClickListener(TableItemClickListener listener);

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
