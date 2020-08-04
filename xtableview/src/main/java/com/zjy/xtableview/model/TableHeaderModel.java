package com.zjy.xtableview.model;

import java.util.List;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表头部视图数据模型
 */
public class TableHeaderModel {

    /**
     * 头部标题
     */
    private String headerTitle;

    /**
     * 各垂直列头部标题
     */
    private List<String> headerData;

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public List<String> getHeaderData() {
        return headerData;
    }

    public void setHeaderData(List<String> headerData) {
        this.headerData = headerData;
    }
}
