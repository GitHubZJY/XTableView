package com.zjy.xtableview.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zjy.xtableview.R;
import com.zjy.xtableview.TableItemClickListener;
import com.zjy.xtableview.model.TableHeaderModel;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表头部视图
 */
public class TableHeaderView extends LinearLayout implements TableItemView.ItemScrollListener {

    private TextView vTitle;
    private LinearLayout vHeaderRow;
    private int mHeaderCellWidth;

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
    }

    public void bindData(TableHeaderModel tableHeaderModel) {
        if (tableHeaderModel == null) {
            return;
        }
        String title = tableHeaderModel.getHeaderTitle();
        List<String> dataList = tableHeaderModel.getHeaderData();
        if (vTitle != null) {
            vTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        }
        for (int i = 0; i < dataList.size(); i++) {
            generateRowCell(tableHeaderModel).setText(dataList.get(i));
        }
    }

    private TextView generateRowCell(final TableHeaderModel headerModel) {
        TextView cellTv = new TextView(getContext());
        cellTv.setTextColor(getResources().getColor(R.color.table_view_second_txt_color));
        cellTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        vHeaderRow.addView(cellTv);
        cellTv.getLayoutParams().width = mHeaderCellWidth;
        cellTv.getLayoutParams().height = MATCH_PARENT;
        cellTv.setGravity(Gravity.CENTER);
        cellTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.clickHeaderCell(headerModel);
                }
            }
        });
        return cellTv;
    }

    public void setCellWidth(int cellWidth) {
        mHeaderCellWidth = cellWidth;
        if (vTitle != null && vTitle.getLayoutParams() != null) {
            vTitle.getLayoutParams().width = cellWidth;
        }
    }

    @Override
    public void scroll(int x) {
        notifyScroll(x);
    }

    public void notifyScroll(int x) {
        vHeaderRow.scrollTo(x, 0);
    }

    private TableItemClickListener mListener;

    public void setTableItemClickListener(TableItemClickListener listener) {
        mListener = listener;
    }
}
