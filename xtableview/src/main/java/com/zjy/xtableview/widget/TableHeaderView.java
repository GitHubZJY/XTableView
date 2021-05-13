package com.zjy.xtableview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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

    private FrameLayout vHead;
    private LinearLayout vHeaderRow;
    private View splitLine;
    private int mHeaderCellWidth;
    private int mHeaderHeadWidth;
    private XTableAdapter mAdapter;
    private TableHeaderListener mListener;

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
        vHead = findViewById(R.id.header_container);
        vHeaderRow = findViewById(R.id.header_data_ll);
        splitLine = findViewById(R.id.split_line);
    }


    public void setTableAdapter(XTableAdapter adapter) {
        mAdapter = adapter;
    }

    public <T> void bindData(Object title, List<T> dataList) {
        generateHeaderCell(title);
        for (int i = 0; i < dataList.size(); i++) {
            generateRowCell(i, dataList.get(i));
        }
    }

    private <T> void generateHeaderCell(final T headerModel) {
        View cellView = mAdapter.onBindTableHeader(headerModel);
        cellView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.clickColumnHeader(0);
                }
            }
        });
        vHead.addView(cellView);
        cellView.getLayoutParams().width = mHeaderCellWidth;
        cellView.getLayoutParams().height = MATCH_PARENT;
    }

    private <T> void generateRowCell(final int position, final T headerModel) {
        View cellView = mAdapter.onBindColumnHeader(position, headerModel);
        cellView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.clickColumnHeader(position + 1);
                }
            }
        });
        vHeaderRow.addView(cellView);
        cellView.getLayoutParams().width = mHeaderCellWidth;
        cellView.getLayoutParams().height = MATCH_PARENT;
    }

    public void setHeaderWidth(int headerWidth) {
        mHeaderHeadWidth = headerWidth;
        if (vHead != null && vHead.getLayoutParams() != null) {
            vHead.getLayoutParams().width = headerWidth;
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

    public void setTableHeaderListener(TableHeaderListener listener) {
        mListener = listener;
    }

    public interface TableHeaderListener {
        void clickColumnHeader(int position);
    }

}
