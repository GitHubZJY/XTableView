package com.zjy.xtableview.widget.swipe;

import android.view.View;


public interface OnItemLongClickListener {

    /**
     * @param view target view.
     * @param adapterPosition position of item.
     */
    void onItemLongClick(View view, int adapterPosition);
}