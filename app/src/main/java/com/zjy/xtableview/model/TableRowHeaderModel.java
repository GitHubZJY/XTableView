package com.zjy.xtableview.model;

/**
 * Date: 2020/8/5
 * Author: Yang
 * Describe:
 */
public class TableRowHeaderModel {

    /**
     * 行头部标题
     */
    private String title;
    /**
     * 行头部描述
     */
    private String detail;

    public TableRowHeaderModel(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

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
}
