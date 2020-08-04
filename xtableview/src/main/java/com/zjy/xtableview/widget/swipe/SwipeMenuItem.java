package com.zjy.xtableview.widget.swipe;

public class SwipeMenuItem {

    private int width = -2;
    private int height = -2;
    private int weight = 0;
    private int layoutId;
    private boolean needConfirm;

    public SwipeMenuItem() {

    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public SwipeMenuItem setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public SwipeMenuItem setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
    }
}