package com.zjy.xtableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zjy.xtableview.adapter.TableItemAdapter;
import com.zjy.xtableview.model.TableHeaderModel;
import com.zjy.xtableview.model.TableItemModel;
import com.zjy.xtableview.utils.DensityUtil;
import com.zjy.xtableview.utils.ScrollHelper;
import com.zjy.xtableview.widget.TableHeaderView;
import com.zjy.xtableview.widget.TouchSwipeRecyclerView;
import com.zjy.xtableview.widget.item.TableCellAdapter;
import com.zjy.xtableview.widget.swipe.OnItemMenuClickListener;
import com.zjy.xtableview.widget.swipe.SwipeMenu;
import com.zjy.xtableview.widget.swipe.SwipeMenuBridge;
import com.zjy.xtableview.widget.swipe.SwipeMenuCreator;
import com.zjy.xtableview.widget.swipe.SwipeMenuItem;
import com.zjy.xtableview.widget.swipe.touch.OnItemMoveListener;

import java.util.Collections;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Date: 2020/7/15
 * Author: Yang
 * Describe: 二维表格布局
 */
public class XTableView extends LinearLayout implements ITableView {

    private TouchSwipeRecyclerView vTableRv;
    private TableHeaderView vHeaderView;
    private TableItemAdapter mItemAdapter;
    private TableCellAdapter mCellAdapter;
    /**
     * 表头高度
     */
    private int mHeaderHeight;
    /**
     * 表行高度
     */
    private int mRowHeight;
    /**
     * 单元格宽度
     */
    private int mCellWidth;

    private ScrollHelper mScrollHelper;

    public XTableView(@NonNull Context context) {
        this(context, null);
    }

    public XTableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XTableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleStyleable(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void handleStyleable(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XTableView, defStyle, 0);
        try {
            mHeaderHeight = ta.getDimensionPixelSize(R.styleable.XTableView_headerHeight, DensityUtil.dp2px(context, 40));
            mRowHeight = ta.getDimensionPixelSize(R.styleable.XTableView_rowHeight, DensityUtil.dp2px(context, 70));
            mCellWidth = ta.getDimensionPixelSize(R.styleable.XTableView_cellWidth, DensityUtil.dp2px(context, 125));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    private void initialize(Context context) {
        mScrollHelper = new ScrollHelper();
        setOrientation(VERTICAL);
        vTableRv = new TouchSwipeRecyclerView(context);
        vTableRv.setOverScrollMode(OVER_SCROLL_NEVER);
        vTableRv.setLongPressDragEnabled(true);
        vHeaderView = new TableHeaderView(context);
        addView(vHeaderView);
        addView(vTableRv);

        vHeaderView.getLayoutParams().height = mHeaderHeight;
        vHeaderView.setCellWidth(mCellWidth);
        vTableRv.setCellWidth(mCellWidth);

        mScrollHelper.addListener(vHeaderView);
        vTableRv.attachScrollHelper(mScrollHelper);
    }

    @Override
    public void bindData(TableHeaderModel headerModel, List<TableItemModel> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        vHeaderView.bindData(headerModel);
        if (mItemAdapter == null) {
            initRv(dataList);
        } else {
            mItemAdapter.setData(dataList);
        }
        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemData(int position, TableItemModel data) {
        if (data == null || mItemAdapter == null || mItemAdapter.getItemList() == null) {
            return;
        }
        mItemAdapter.getItemList().set(position, data);
        mItemAdapter.notifyItemChanged(position, 1);
    }

    @Override
    public void setCellAdapter(TableCellAdapter cellAdapter) {
        mCellAdapter = cellAdapter;
    }

    private void initRv(final List<TableItemModel> dataList) {
        if (mCellAdapter == null) {
            throw new IllegalStateException("Please call setCellAdapter() first before bindData.");
        }
        vTableRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mItemAdapter = new TableItemAdapter(getContext(), dataList, mCellWidth, mRowHeight, mScrollHelper, mCellAdapter);

        vTableRv.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem();
                deleteItem.setWidth(DensityUtil.dp2px(getContext(), 125));
                deleteItem.setHeight(MATCH_PARENT);
                deleteItem.setNeedConfirm(true);
                deleteItem.setLayoutId(R.layout.table_swipe_menu_layout);
                leftMenu.addMenuItem(deleteItem);
            }
        });


        vTableRv.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                menuBridge.closeMenu();
                mItemAdapter.getItemList().remove(position);
                mItemAdapter.notifyItemRemoved(position);
            }
        });

        vTableRv.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();
                Collections.swap(mItemAdapter.getItemList(), fromPosition, toPosition);
                mItemAdapter.notifyItemMoved(fromPosition, toPosition);
                mItemAdapter.notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition) + 1);

                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        });

        vTableRv.setAdapter(mItemAdapter);
    }

    @Override
    public void setTableItemClickListener(TableItemClickListener listener) {
        if (mItemAdapter != null) {
            mItemAdapter.setTableItemClickListener(listener);
        }
    }

    @Override
    public void setLongPressDragEnable(boolean enable) {
        if (vTableRv != null) {
            vTableRv.setLongPressDragEnabled(enable);
        }
    }

    @Override
    public void setSwipeEnable(boolean enable) {
        if (vTableRv != null) {
            vTableRv.enableSwipe(enable);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScrollHelper.destroy();
    }
}
