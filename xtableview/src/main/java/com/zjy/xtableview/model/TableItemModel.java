package com.zjy.xtableview.model;

import java.util.List;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表行视图数据模型
 */
public class TableItemModel {

    /**
     * 行头部标题
     */
    private String title;
    /**
     * 行头部描述
     */
    private String detail;
    /**
     * 行内容数据
     */
    private List<TableItemCellModel> dataList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<TableItemCellModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<TableItemCellModel> dataList) {
        this.dataList = dataList;
    }
}
