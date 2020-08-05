package com.zjy.xtableview.adapter;

import android.content.Context;
import android.view.View;

import com.zjy.xtableview.model.TableRowModel;

/**
 * Date: 2020/8/4
 * Author: Yang
 * Describe: 表格数据填充适配器
 */
public abstract class XTableAdapter<T, H extends TableRowModel<?, ?>> {

    private Context mContext;

    public XTableAdapter(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 创建列头部视图
     * @param position 列下标
     * @return 列视图
     */
    public abstract View onBindHeader(int position, T t);

    /**
     * 创建每一格的视图
     * @param position 行下标
     * @return 行视图
     */
    public abstract View onCreateTableItem(int position);

//    public void onBindData(int position, View view, H rowData) {
//        onBindTableItem(position, view, rowData.content.get(position));
//    }

    /**
     * 绑定每一格的视图数据
     * @param position 行下标
     * @param view 单元格根视图
     */
    public abstract void onBindTableItem(int position, View view, H rowModel);

    /**
     * 创建每一行的头部视图
     * @param position 行下标
     * @return 头部视图
     */
    public abstract View onCreateRowHeader(int position);

    /**
     * 绑定每一行的头部视图数据
     * @param position 行下标
     * @param view 单元格根视图
     */
    public abstract void onBindRowHeader(int position, View view, H rowModel);

    protected String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    protected int getColor(int resId) {
        return getContext().getResources().getColor(resId);
    }

}
