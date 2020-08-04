package com.zjy.xtableview.widget.item;

import android.view.View;
import android.view.ViewGroup;

import com.zjy.xtableview.model.TableItemCellModel;

/**
 * Date: 2020/8/4
 * Author: Yang
 * Describe: 单元格适配器抽象接口
 */
public abstract class TableCellAdapter<T extends View> {

    public abstract T getView(int position, TableItemCellModel cellModel, ViewGroup parent);

    public abstract void bindData(int position, TableItemCellModel cellModel, T itemView);

}
