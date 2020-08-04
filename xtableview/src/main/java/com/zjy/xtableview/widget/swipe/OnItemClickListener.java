package com.zjy.xtableview.widget.swipe;

import android.view.View;


public interface OnItemClickListener {

    /**
     * @param view target view.
     * @param adapterPosition position of item.
     */
    void onItemClick(View view, int adapterPosition);
}