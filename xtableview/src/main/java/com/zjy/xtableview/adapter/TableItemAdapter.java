package com.zjy.xtableview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zjy.xtableview.R;
import com.zjy.xtableview.model.TableRowModel;
import com.zjy.xtableview.utils.ScrollHelper;
import com.zjy.xtableview.widget.TableItemView;

import java.util.List;

/**
 * Date: 2020/7/16
 * Author: Yang
 * Describe: 二维列表适配器
 */
public class TableItemAdapter<T extends TableRowModel<?, ?>> extends RecyclerView.Adapter<TableItemAdapter.ViewHolder> {

    private Context mContext;
    private List<T> mItemList;
    private int mMinHeight;
    private int mCellWidth;
    private ScrollHelper mScrollHelper;
    private XTableAdapter<?, ?> mCellAdapter;
    private ItemConfig mItemConfig;

    public TableItemAdapter(Context context, List<T> dataList, int cellWidth, int minItemHeight, ScrollHelper helper, XTableAdapter<?, ?> cellAdapter, ItemConfig itemConfig) {
        this.mItemList = dataList;
        this.mContext = context;
        this.mMinHeight = minItemHeight;
        this.mCellWidth = cellWidth;
        this.mScrollHelper = helper;
        this.mCellAdapter = cellAdapter;
        this.mItemConfig = itemConfig;
    }

    public void setData(List<T> itemList) {
        mItemList = itemList;
    }

    public List<T> getItemList() {
        return mItemList;
    }

    @NonNull
    @Override
    public TableItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_item, null);
        ((TableItemView) view).attachScrollHelper(mScrollHelper);
        ((TableItemView) view).setItemConfig(mItemConfig);
        return new ViewHolder(view, mCellWidth, mMinHeight);
    }

    @Override
    public void onBindViewHolder(@NonNull final TableItemAdapter.ViewHolder holder, final int position) {
        mScrollHelper.bindTableItemView(position, holder.vItemView);
        holder.vItemView.setAdapter(mCellAdapter);
        holder.vItemView.bindData(position, mItemList.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        mScrollHelper.bindTableItemView(position, holder.vItemView);
        holder.vItemView.setAdapter(mCellAdapter);
        if (payloads.size() > 0) {
            holder.vItemView.notifyDetailData(position, mItemList.get(position));
        } else {
            holder.vItemView.bindData(position, mItemList.get(position));
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.vItemView.cancelAnim();
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TableItemView vItemView;

        public ViewHolder(@NonNull View itemView, int cellWidth, int minHeight) {
            super(itemView);
            vItemView = itemView.findViewById(R.id.table_item_view);
            vItemView.setMinHeight(minHeight);
            vItemView.setCellWidth(cellWidth);
        }
    }
}
