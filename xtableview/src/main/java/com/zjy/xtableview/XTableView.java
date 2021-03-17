package com.zjy.xtableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zjy.xtableview.adapter.ItemConfig;
import com.zjy.xtableview.adapter.TableItemAdapter;
import com.zjy.xtableview.adapter.XTableAdapter;
import com.zjy.xtableview.model.TableRowModel;
import com.zjy.xtableview.utils.DensityUtil;
import com.zjy.xtableview.utils.ScrollHelper;
import com.zjy.xtableview.widget.TableHeaderView;
import com.zjy.xtableview.widget.TouchSwipeRecyclerView;
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
public class XTableView extends LinearLayout implements ITableView, XTableAdapter.TableDataObserver {

    private TouchSwipeRecyclerView vTableRv;
    private LinearLayoutManager mLayoutManager;
    private TableHeaderView vHeaderView;
    private TableItemAdapter mItemAdapter;
    private XTableAdapter<?, ?> mTableAdapter;
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
    /**
     * 侧滑布局id
     */
    private int mSwipeLayoutId;
    /**
     * 刷新动画开关
     */
    private boolean mNotifyAnim;
    /**
     * 滑动逻辑
     */
    private ScrollHelper mScrollHelper;
    /**
     * 监听器
     */
    private XTableListener mTableListener;

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
            mSwipeLayoutId = ta.getResourceId(R.styleable.XTableView_swipeLayout, R.layout.table_swipe_menu_layout);
            mNotifyAnim = ta.getBoolean(R.styleable.XTableView_notifyAnim, false);
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

    private <T, H> void bindData(String title, List<T> headerModel, List<H> dataList) {
        if (dataList == null) {
            return;
        }
        vHeaderView.bindData(title, headerModel);
        if (mItemAdapter == null) {
            initRv(dataList);
        } else {
            mItemAdapter.setData(dataList);
        }
        mItemAdapter.notifyDataSetChanged();
    }

    private <T extends TableRowModel<?, ?>> void notifyItemData(int position, T data) {
        if (data == null || mItemAdapter == null || mItemAdapter.getItemList() == null) {
            return;
        }
        mItemAdapter.getItemList().set(position, data);
        mItemAdapter.notifyItemChanged(position, 1);
    }

    private <T extends TableRowModel<?, ?>> void notifyInsertData(int position, T data) {
        if (data == null || mItemAdapter == null || mItemAdapter.getItemList() == null) {
            return;
        }
        mItemAdapter.getItemList().add(position, data);
        mItemAdapter.notifyItemInserted(position);
        //刷新一遍，否则滑动监听同步会有问题
        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTableAdapter(XTableAdapter<?, ?> adapter) {
        mTableAdapter = adapter;
        mTableAdapter.registerDataSetObserver(this);
        if (vHeaderView != null) {
            vHeaderView.setTableAdapter(adapter);
        }
        mTableAdapter.notifyDataSetChanged();
    }

    private <T> void initRv(final List<T> dataList) {
        if (mTableAdapter == null) {
            throw new IllegalStateException("Please call setTableAdapter() first before bindData.");
        }
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        vTableRv.setLayoutManager(mLayoutManager);
        ItemConfig itemConfig = new ItemConfig();
        itemConfig.setNotifyAnim(mNotifyAnim);
        mItemAdapter = new TableItemAdapter(getContext(), dataList, mCellWidth, mRowHeight, mScrollHelper, mTableAdapter, itemConfig);

        vTableRv.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem();
                deleteItem.setWidth(DensityUtil.dp2px(getContext(), 125));
                deleteItem.setHeight(MATCH_PARENT);
                deleteItem.setNeedConfirm(true);
                deleteItem.setLayoutId(mSwipeLayoutId);
                leftMenu.addMenuItem(deleteItem);
            }
        });


        vTableRv.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                menuBridge.closeMenu();
                mItemAdapter.getItemList().remove(position);
                mItemAdapter.notifyItemRemoved(position);
                if (mTableListener != null) {
                    mTableListener.clickSwipeMenu(position);
                }
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
                if (mTableListener != null) {
                    mTableListener.onItemMove(fromPosition, toPosition);
                }
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        });

        vTableRv.setAdapter(mItemAdapter);
    }

    @Override
    public void onDataChange() {
        if (mTableAdapter == null) {
            return;
        }
        bindData(mTableAdapter.getHeader(), mTableAdapter.getColumnHeaderData(), mTableAdapter.getTableData());
    }

    @Override
    public void onItemChange(int position, TableRowModel<?, ?> data) {
        notifyItemData(position, data);
    }

    @Override
    public void onItemInsert(int position, TableRowModel<?, ?> data) {
        notifyInsertData(position, data);
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
    public void setReverseLayout(boolean reserveLayout) {
        if (mLayoutManager != null) {
            mLayoutManager.setReverseLayout(reserveLayout);
        }
    }

    @Override
    public void setStackFromEnd(boolean stackFromEnd) {
        if (mLayoutManager != null) {
            mLayoutManager.setStackFromEnd(stackFromEnd);
        }
    }

    @Override
    public void scrollToPosition(int position) {
        if (mLayoutManager != null) {
            mLayoutManager.scrollToPosition(position);
        }
    }

    @Override
    public void scrollToPositionWithOffset(int position, int offset) {
        if (mLayoutManager != null) {
            mLayoutManager.scrollToPositionWithOffset(position, offset);
        }
    }

    @Override
    public void setTableListener(XTableListener tableListener) {
        this.mTableListener = tableListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScrollHelper.destroy();
        mTableAdapter.unRegisterDataSetObserver();
    }
}
