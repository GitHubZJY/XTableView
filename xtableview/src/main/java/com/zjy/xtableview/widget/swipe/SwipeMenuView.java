package com.zjy.xtableview.widget.swipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class SwipeMenuView extends LinearLayout implements View.OnClickListener {

    private RecyclerView.ViewHolder mViewHolder;
    private OnItemMenuClickListener mItemClickListener;

    public SwipeMenuView(Context context) {
        this(context, null);
    }

    public SwipeMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void createMenu(RecyclerView.ViewHolder viewHolder, SwipeMenu swipeMenu, Controller controller,
                           int direction, OnItemMenuClickListener itemClickListener) {
        removeAllViews();

        this.mViewHolder = viewHolder;
        this.mItemClickListener = itemClickListener;
        List<SwipeMenuItem> items = swipeMenu.getMenuItems();
        for (int i = 0; i < items.size(); i++) {
            SwipeMenuItem item = items.get(i);

            LayoutParams params = new LayoutParams(item.getWidth(), item.getHeight());
            params.weight = item.getWeight();
            View rootView = LayoutInflater.from(getContext()).inflate(item.getLayoutId(), null);
            rootView.setLayoutParams(params);
            rootView.setOnClickListener(this);
            addView(rootView);

            SwipeMenuBridge menuBridge = new SwipeMenuBridge(controller, direction, i);
            menuBridge.setNeedConfirm(item.isNeedConfirm());
            rootView.setTag(menuBridge);
        }
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            SwipeMenuBridge menuTag = (SwipeMenuBridge) v.getTag();
            if (!menuTag.isNeedConfirm()) {
                mItemClickListener.onItemClick((SwipeMenuBridge) v.getTag(), mViewHolder.getAdapterPosition());
                return;
            }
            if (menuTag.hasConfirm()) {
                mItemClickListener.onItemClick((SwipeMenuBridge) v.getTag(), mViewHolder.getAdapterPosition());
            } else {
                menuTag.setConfirm(true);
                v.setTag(menuTag);
                if (v instanceof LinearLayout) {
                    ((LinearLayout) v).getChildAt(0).setVisibility(VISIBLE);
                    TextView menuTv = (TextView) ((LinearLayout) v).getChildAt(0);
                    menuTv.setText("确认删除");
                }
            }
        }
    }

    public void resetMenu() {
        View rootView = getChildAt(0);
        if (rootView == null) {
            return;
        }
        View menuTv = ((ViewGroup) rootView).getChildAt(0);
        SwipeMenuBridge menuTag = (SwipeMenuBridge) rootView.getTag();
        if (menuTag != null) {
            menuTag.setConfirm(false);
        }
        if (menuTv instanceof TextView) {
            ((TextView) menuTv).setText("删除");
        }
    }
}