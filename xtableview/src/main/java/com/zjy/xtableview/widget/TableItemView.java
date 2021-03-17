package com.zjy.xtableview.widget;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zjy.xtableview.R;
import com.zjy.xtableview.adapter.ItemConfig;
import com.zjy.xtableview.adapter.XTableAdapter;
import com.zjy.xtableview.model.TableRowModel;
import com.zjy.xtableview.utils.DensityUtil;
import com.zjy.xtableview.utils.ScrollHelper;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表单行视图（包括单行表头和行列表）
 */
@SuppressWarnings("rawtypes")
public class TableItemView extends ConstraintLayout {

    private View vRootView;
    private LinearLayout vTitleLayout;
    private ScrollLinearLayout vDataLl;
    private View splitLine;
    private ScrollHelper mScrollHelper;
    private ObjectAnimator mAnimator;
    private boolean mNotifyAnim = false;
    private int mCellWidth;

    private XTableAdapter mAdapter;

    public TableItemView(Context context) {
        this(context, null);
    }

    public TableItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.table_item_layout, this);
        vRootView = findViewById(R.id.root_view);
        vTitleLayout = findViewById(R.id.title_layout);
        vDataLl = findViewById(R.id.data_ll);
        vDataLl.setOrientation(LinearLayout.HORIZONTAL);
        splitLine = findViewById(R.id.split_line);
    }

    public void attachScrollHelper(ScrollHelper helper) {
        mScrollHelper = helper;
        vDataLl.attachScrollHelper(helper);
    }

    public void setItemConfig(ItemConfig itemConfig) {
        if (itemConfig == null) {
            return;
        }
        mNotifyAnim = itemConfig.isNotifyAnim();
    }

    public void setMinHeight(int height) {
        vTitleLayout.setMinimumHeight(height);
    }

    public void setAdapter(XTableAdapter adapter) {
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;
    }

    public <T extends TableRowModel> void bindData(int position, T rowData) {
        if (vTitleLayout.getChildCount() == 0) {
            View headerView = mAdapter.onCreateRowHeader(position);
            vTitleLayout.addView(headerView);
            headerView.getLayoutParams().width = mCellWidth;
            headerView.getLayoutParams().height = MATCH_PARENT;
        }
        mAdapter.onBindRowHeader(position, vTitleLayout, rowData);
        List<?> dataList = rowData.rowData;
        if (dataList != null) {
            mScrollHelper.setColumnCount(dataList.size());
            int columnCount = mScrollHelper.getColumnCount();
            int maxScrollDistance = mCellWidth * (columnCount + 1) - DensityUtil.getScreenWidth(getContext());
            vDataLl.setMaxScrollDistance(maxScrollDistance);
            for (int i = 0; i < dataList.size(); i++) {
                //TODO data.get(i)
                bindCellView(i, rowData);
            }

        }
    }

    private <T extends TableRowModel> void bindCellView(int position, final T cellModel) {
        if (vDataLl.getChildAt(position) == null) {
            View cellView = mAdapter.onCreateTableItem(position);
            vDataLl.addView(cellView);
            cellView.getLayoutParams().width = mCellWidth;
            cellView.getLayoutParams().height = MATCH_PARENT;
        }
        mAdapter.onBindTableItem(position, vDataLl.getChildAt(position), cellModel);
    }

    public <T extends TableRowModel> void notifyDetailData(int position, T tableItemModel) {
        if (tableItemModel == null || tableItemModel.getRowData() == null || tableItemModel.getRowData().isEmpty()) {
            return;
        }
        bindData(position, tableItemModel);
        startAnim();
    }

    public void setCellWidth(int cellWidth) {
        mCellWidth = cellWidth;
        if (vTitleLayout != null && vTitleLayout.getLayoutParams() != null) {
            vTitleLayout.getLayoutParams().width = cellWidth;
        }
    }

    public LinearLayout getRowView() {
        return vDataLl;
    }

    public void notifyScroll(final int x) {
        vDataLl.scrollTo(x, 0);
        splitLine.setVisibility(x != 0 ? VISIBLE : INVISIBLE);
    }

    public void notifyScrollFilling(boolean isLeft) {
        vDataLl.scrollFilling(isLeft);
    }

    public void stopScroll() {
        vDataLl.stopScroll();
    }

    private void startAnim() {
        if (!mNotifyAnim) {
            return;
        }
        if (mAnimator != null && mAnimator.isRunning()) {
            return;
        }
        int colorA = Color.parseColor("#4328B464");
        int colorB = Color.parseColor("#FFFFFF");
        mAnimator = ObjectAnimator.ofInt(vRootView, "backgroundColor", colorA, colorB);
        mAnimator.setDuration(900);
        mAnimator.setEvaluator(new ArgbEvaluator());
        mAnimator.start();
    }

    public void cancelAnim() {
        if (!mNotifyAnim) {
            return;
        }
        vRootView.setBackgroundColor(Color.WHITE);
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    public void setNotifyAnim(boolean hasAnim) {
        mNotifyAnim = hasAnim;
    }

    public interface ItemScrollListener {
        void scroll(int x);
    }
}
