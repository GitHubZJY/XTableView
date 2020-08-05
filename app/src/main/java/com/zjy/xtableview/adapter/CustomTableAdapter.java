package com.zjy.xtableview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zjy.xtableview.R;
import com.zjy.xtableview.model.TableRowCellModel;
import com.zjy.xtableview.model.TableRowHeaderModel;
import com.zjy.xtableview.model.TableRowModel;

/**
 * Date: 2020/8/5
 * Author: Yang
 * Describe:
 */
public class CustomTableAdapter extends XTableAdapter<String, TableRowModel<TableRowHeaderModel, TableRowCellModel>>{

    public CustomTableAdapter(Context context) {
        super(context);
    }

    @Override
    public View onBindHeader(int position, String t) {
        TextView cellTv = new TextView(getContext());
        cellTv.setTextColor(getColor(R.color.table_view_second_txt_color));
        cellTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        cellTv.setGravity(Gravity.CENTER);
        cellTv.setText(t);
        return cellTv;
    }

    @Override
    public View onCreateTableItem(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.table_item_cell_layout, null);
        TextView cellTv = view.findViewById(R.id.cell_tv);
        cellTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        cellTv.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public void onBindTableItem(int position, View view, TableRowModel<TableRowHeaderModel, TableRowCellModel> rowModel) {
        final TableRowCellModel cellModel = rowModel.getRowData().get(position);
        TextView cellTv = view.findViewById(R.id.cell_tv);
        cellTv.setTextColor(cellModel.isRise() ?
                getColor(R.color.table_view_rise_txt_color)
                : getColor(R.color.table_view_fall_txt_color));
        cellTv.setText(cellModel.getContent());
        cellTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), cellModel.getContent(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateRowHeader(int position) {
        return LayoutInflater.from(getContext()).inflate(R.layout.table_item_title_layout, null);
    }

    @Override
    public void onBindRowHeader(int position, View view, TableRowModel<TableRowHeaderModel, TableRowCellModel> rowModel) {
        TextView vTitle = view.findViewById(R.id.title_tv);
        TextView vDetail = view.findViewById(R.id.detail_tv);
        String title = rowModel.rowHeader.getTitle();
        String detail = rowModel.rowHeader.getDetail();
        if (vTitle != null) {
            vTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        }
        if (vDetail != null) {
            vDetail.setText(TextUtils.isEmpty(detail) ? "" : detail);
        }
    }
}
