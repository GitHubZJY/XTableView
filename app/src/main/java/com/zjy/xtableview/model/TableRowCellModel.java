package com.zjy.xtableview.model;

/**
 * Date: 2020/7/20
 * Author: Yang
 * Describe: 单个格子数据模型
 */
public class TableRowCellModel {

    private static final int RISE_CELL = 1;
    private static final int FALL_CELL = 2;

    private String content;

    private int type;

    public TableRowCellModel(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public static TableRowCellModel createRiseCell(String content) {
        return new TableRowCellModel(content, RISE_CELL);
    }

    public static TableRowCellModel createFallCell(String content) {
        return new TableRowCellModel(content, FALL_CELL);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRise() {
        return type == RISE_CELL;
    }
}
