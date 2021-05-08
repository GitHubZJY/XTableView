package com.zjy.xtableview.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zjy.xtableview.R;
import com.zjy.xtableview.adapter.XTableAdapter;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表头部视图
 */
@SuppressWarnings("rawtypes")
public class TableHeaderView extends LinearLayout implements TableItemView.ItemScrollListener {

    private TextView vTitle;
    private LinearLayout vHeaderRow;
    private View splitLine;
    private int mHeaderCellWidth;
    private int mHeaderHeadWidth;
    private XTableAdapter mAdapter;

    public TableHeaderView(Context context) {
        this(context, null);
    }

    public TableHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.table_header_layout, this);
        vTitle = findViewById(R.id.header_title_tv);
        vHeaderRow = findViewById(R.id.header_data_ll);
        splitLine = findViewById(R.id.split_line);
    }


    public void setTableAdapter(XTableAdapter adapter) {
        mAdapter = adapter;
    }

    public <T> void bindData(String title, List<T> dataList) {
        if (vTitle != null) {
            vTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        }
        for (int i = 0; i < dataList.size(); i++) {
            generateRowCell(i, dataList.get(i));
        }
    }

    private <T> void generateRowCell(int position, final T headerModel) {
        View cellTv = mAdapter.onBindHeader(position, headerModel);
        vHeaderRow.addView(cellTv);
        cellTv.getLayoutParams().width = mHeaderCellWidth;
        cellTv.getLayoutParams().height = MATCH_PARENT;
    }

    public void setHeaderWidth(int headerWidth) {
        mHeaderHeadWidth = headerWidth;
        if (vTitle != null && vTitle.getLayoutParams() != null) {
            vTitle.getLayoutParams().width = headerWidth;
        }
    }

    public void setCellWidth(int cellWidth) {
        mHeaderCellWidth = cellWidth;
    }

    @Override
    public void scroll(int x) {
        notifyScroll(x);
    }

    public void notifyScroll(int x) {
        vHeaderRow.scrollTo(x, 0);
        splitLine.setVisibility(x != 0 ? VISIBLE : INVISIBLE);
    }

}
