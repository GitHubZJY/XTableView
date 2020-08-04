package com.zjy.xtableview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zjy.xtableview.R;
import com.zjy.xtableview.TableItemClickListener;
import com.zjy.xtableview.model.TableItemModel;
import com.zjy.xtableview.utils.ScrollHelper;
import com.zjy.xtableview.widget.TableItemView;

import java.util.List;

/**
 * Date: 2020/7/16
 * Author: Yang
 * Describe: 二维列表适配器
 */
public class TableItemAdapter extends RecyclerView.Adapter<TableItemAdapter.ViewHolder> {

    private Context mContext;
    private List<TableItemModel> mItemList;
    private TableItemClickListener mListener;
    private int mMinHeight;
    private int mCellWidth;
    private ScrollHelper mScrollHelper;

    public TableItemAdapter(Context context, List<TableItemModel> dataList, int cellWidth, int minItemHeight, ScrollHelper helper) {
        this.mItemList = dataList;
        this.mContext = context;
        this.mMinHeight = minItemHeight;
        this.mCellWidth = cellWidth;
        this.mScrollHelper = helper;
    }

    public void setTableItemClickListener(TableItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<TableItemModel> itemList) {
        mItemList = itemList;
    }

    public List<TableItemModel> getItemList() {
        return mItemList;
    }

    @NonNull
    @Override
    public TableItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_item, null);
        ((TableItemView) view).attachScrollHelper(mScrollHelper);
        return new ViewHolder(view, mCellWidth, mMinHeight);
    }

    @Override
    public void onBindViewHolder(@NonNull final TableItemAdapter.ViewHolder holder, final int position) {
        mScrollHelper.bindTableItemView(position, holder.vItemView);
        holder.vItemView.bindData(mItemList.get(position));
        holder.vItemView.setTableItemClickListener(mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        mScrollHelper.bindTableItemView(position, holder.vItemView);
        if (payloads.size() > 0) {
            holder.vItemView.notifyDetailData(mItemList.get(position).getDataList());
        } else {
            holder.vItemView.bindData(mItemList.get(position));
        }
        holder.vItemView.setTableItemClickListener(mListener);
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
