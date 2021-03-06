package com.zjy.xtableview.adapter;

import android.content.Context;
import android.view.View;

import com.zjy.xtableview.model.TableRowModel;

import java.util.List;

/**
 * Date: 2020/8/4
 * Author: Yang
 * Describe: 表格数据填充适配器
 */
public abstract class XTableAdapter<T, H extends TableRowModel<?, ?>> {

    private Context mContext;
    /**
     * 左上角的表头数据
     */
    private T mHeader;
    /**
     * 列头部数据集合
     */
    private List<T> mColumnHeader;
    /**
     * 每一行的数据集合
     */
    private List<H> mTableData;
    /**
     * 数据监听
     */
    private TableDataObserver mObserver;

    public XTableAdapter(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 更新表格数据
     * @param header 左上角的表头数据
     * @param columnHeader 列头部数据集合
     * @param tableData 每一行的数据集合
     */
    public void bindData(T header, List<T> columnHeader, List<H> tableData) {
        mHeader = header;
        mColumnHeader = columnHeader;
        mTableData = tableData;
    }

    /**
     * 更新某一行数据
     * @param position 行下标
     * @param data 更新的内容
     */
    public void notifyItemData(int position, H data) {
        if (mObserver != null) {
            mObserver.onItemChange(position, data);
        }
    }

    /**
     * 插入某一行数据
     * @param position 行下标
     * @param data 插入的内容
     */
    public void notifyInsertData(int position, H data) {
        if (mObserver != null) {
            mObserver.onItemInsert(position, data);
        }
    }

    public T getHeader() {
        return mHeader;
    }

    public List<T> getColumnHeaderData() {
        return mColumnHeader;
    }

    public List<H> getTableData() {
        return mTableData;
    }

    /**
     * 创建表头部视图（即左上角重合的单元格）
     * 考虑到可能有特殊的样式需求，所以抽取出来
     * @return 表头部视图
     */
    public abstract View onBindTableHeader(T t);

    /**
     * 创建列头部视图
     * @param position 列下标
     * @return 列视图
     */
    public abstract View onBindColumnHeader(int position, T t);

    /**
     * 创建每一格的视图
     * @param position 行下标
     * @return 行视图
     */
    public abstract View onCreateTableItem(int position);

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

    public void notifyDataSetChanged() {
        if (mObserver != null) {
            mObserver.onDataChange();
        }
    }

    public void registerDataSetObserver(TableDataObserver observer) {
        mObserver = observer;
    }

    public void unRegisterDataSetObserver() {
        mObserver = null;
    }

    public interface TableDataObserver {
        /**
         * 数据源发生变更
         */
        void onDataChange();

        /**
         * 更新某一行数据
         * @param position 行下标
         * @param data 覆盖的数据
         */
        void onItemChange(int position, TableRowModel<?, ?> data);

        /**
         * 插入某一行数据
         * @param position 行下标
         * @param data 插入的数据
         */
        void onItemInsert(int position, TableRowModel<?, ?> data);
    }

}
