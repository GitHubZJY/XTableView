package com.zjy.xtableview.widget;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zjy.xtableview.R;
import com.zjy.xtableview.TableItemClickListener;
import com.zjy.xtableview.model.TableItemCellModel;
import com.zjy.xtableview.model.TableItemModel;
import com.zjy.xtableview.utils.DensityUtil;
import com.zjy.xtableview.utils.ScrollHelper;
import com.zjy.xtableview.widget.item.TableCellAdapter;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表单行视图（包括单行表头和行列表）
 */
public class TableItemView extends ConstraintLayout {

    private View vRootView;
    private ConstraintLayout vTitleLayout;
    private TextView vTitle;
    private TextView vDetail;
    private ScrollLinearLayout vDataLl;
    private ScrollHelper mScrollHelper;
    private ObjectAnimator mAnimator;
    private boolean mNotifyAnim = false;
    private int mCellWidth;

    private TableCellAdapter<View> mAdapter;

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
        vTitle = findViewById(R.id.title_tv);
        vDetail = findViewById(R.id.detail_tv);
        vDataLl = findViewById(R.id.data_ll);
    }

    public void attachScrollHelper(ScrollHelper helper) {
        mScrollHelper = helper;
        vDataLl.attachScrollHelper(helper);
    }

    public void setMinHeight(int height) {
        vTitleLayout.setMinHeight(height);
    }

    public void setAdapter(TableCellAdapter<View> adapter) {
        if (adapter == null) {
            return;
        }
        mAdapter = adapter;
    }

    public void bindData(TableItemModel tableItemModel) {
        if (tableItemModel == null) {
            return;
        }
        String title = tableItemModel.getTitle();
        String detail = tableItemModel.getDetail();
        List<TableItemCellModel> dataList = tableItemModel.getDataList();
        if (vTitle != null) {
            vTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        }
        if (vDetail != null) {
            vDetail.setText(TextUtils.isEmpty(detail) ? "" : detail);
        }
        if (dataList != null) {
            mScrollHelper.setColumnCount(dataList.size());
            int columnCount = mScrollHelper.getColumnCount();
            int maxScrollDistance = mCellWidth * (columnCount + 1) - DensityUtil.getScreenWidth(getContext());
            vDataLl.setMaxScrollDistance(maxScrollDistance);
            for (int i = 0; i < dataList.size(); i++) {
                bindCellView(i, dataList.get(i));
            }

        }
    }

    private void bindCellView(int position, final TableItemCellModel cellModel) {
        if (vDataLl.getChildAt(position) != null) {
            mAdapter.bindData(position, cellModel, vDataLl.getChildAt(position));
        } else {
            View cellView = mAdapter.getView(position, cellModel, vDataLl);
            vDataLl.addView(cellView);
            cellView.getLayoutParams().width = mCellWidth;
            cellView.getLayoutParams().height = MATCH_PARENT;
            cellView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.clickCell(cellModel);
                    }
                }
            });
        }
    }

    public void notifyDetailData(TableItemModel tableItemModel) {
        if (tableItemModel == null || tableItemModel.getDataList() == null || tableItemModel.getDataList().isEmpty()) {
            return;
        }
        bindData(tableItemModel);
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

    private TableItemClickListener mListener;

    public void setTableItemClickListener(TableItemClickListener listener) {
        mListener = listener;
    }

    public interface ItemScrollListener {
        void scroll(int x);
    }
}
