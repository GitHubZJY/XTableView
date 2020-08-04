package com.zjy.xtableview.widget.swipe;


public class SwipeMenuBridge {

    private final Controller mController;
    private final int mDirection;
    private final int mPosition;
    private boolean mConfirm;
    private boolean needConfirm;

    public SwipeMenuBridge(Controller controller, int direction, int position) {
        this.mController = controller;
        this.mDirection = direction;
        this.mPosition = position;
    }

    @SwipeRecyclerView.DirectionMode
    public int getDirection() {
        return mDirection;
    }

    /**
     * Get the position of button in the menu.
     */
    public int getPosition() {
        return mPosition;
    }

    public void closeMenu() {
        mConfirm = false;
        mController.smoothCloseMenu();
    }

    public boolean hasConfirm() {
        return mConfirm;
    }

    public void setConfirm(boolean confirm) {
        mConfirm = confirm;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public void setNeedConfirm(boolean needConfirm) {
        this.needConfirm = needConfirm;
    }
}