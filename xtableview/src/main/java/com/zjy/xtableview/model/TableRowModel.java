package com.zjy.xtableview.model;

import java.util.List;

/**
 * Date: 2020/8/5
 * Author: Yang
 * Describe: 二维表格每一行的数据模型
 */
public class TableRowModel<T, H> {

    /**
     * 每行的头部数据
     */
    public T rowHeader;

    /**
     * 每行的行数据集合
     */
    public List<H> rowData;

    public T getRowHeader() {
        return rowHeader;
    }

    public void setRowHeader(T rowHeader) {
        this.rowHeader = rowHeader;
    }

    public List<H> getRowData() {
        return rowData;
    }

    public void setRowData(List<H> rowData) {
        this.rowData = rowData;
    }
}
