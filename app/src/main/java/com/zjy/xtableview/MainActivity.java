package com.zjy.xtableview;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zjy.xtableview.adapter.CustomTableAdapter;
import com.zjy.xtableview.model.TableHeaderModel;
import com.zjy.xtableview.model.TableRowCellModel;
import com.zjy.xtableview.model.TableRowHeaderModel;
import com.zjy.xtableview.model.TableRowModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TableRowModel<TableRowHeaderModel, TableRowCellModel>> mDataList;
    private TableHeaderModel mHeaderModel;
    private CustomTableAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        final ITableView vTableView = findViewById(R.id.table_view);
        vTableView.setLongPressDragEnable(true);
        vTableView.setSwipeEnable(true);
        mAdapter = new CustomTableAdapter(this);
        mAdapter.bindData(mHeaderModel.getHeaderTitle(), mHeaderModel.getHeaderData(), mDataList);
        vTableView.setTableAdapter(mAdapter);



        testNotify();

    }

    private void initData() {
        mDataList = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            final TableRowModel<TableRowHeaderModel, TableRowCellModel> model = new TableRowModel<>();
            model.setRowHeader(new TableRowHeaderModel("横向标题1", "横向描述1"));
            List<TableRowCellModel> childData = new ArrayList<>();
            childData.add(TableRowCellModel.createRiseCell("123"));
            childData.add(TableRowCellModel.createRiseCell("15%"));
            childData.add(TableRowCellModel.createRiseCell("22"));
            childData.add(TableRowCellModel.createRiseCell("112"));
            childData.add(TableRowCellModel.createFallCell("-123"));
            childData.add(TableRowCellModel.createFallCell("-99%"));
            model.setRowData(childData);
            mDataList.add(model);

            final TableRowModel<TableRowHeaderModel, TableRowCellModel> model2 = new TableRowModel<>();
            model2.setRowHeader(new TableRowHeaderModel("横向标题2", "横向描述2"));
            List<TableRowCellModel> childData2 = new ArrayList<>();
            childData2.add(TableRowCellModel.createRiseCell("13"));
            childData2.add(TableRowCellModel.createFallCell("-20%"));
            childData2.add(TableRowCellModel.createRiseCell("12"));
            childData2.add(TableRowCellModel.createRiseCell("10"));
            childData2.add(TableRowCellModel.createRiseCell("057"));
            childData2.add(TableRowCellModel.createRiseCell("62%"));
            model2.setRowData(childData2);
            mDataList.add(model2);
        }

        mHeaderModel = new TableHeaderModel();
        mHeaderModel.setHeaderTitle("表头");
        List<String> headerData = new ArrayList<>();
        headerData.add("纵向1");
        headerData.add("纵向2");
        headerData.add("纵向3");
        headerData.add("纵向4");
        headerData.add("纵向5");
        headerData.add("纵向6");
        mHeaderModel.setHeaderData(headerData);


    }

    private void testNotify() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final TableRowModel<TableRowHeaderModel, TableRowCellModel> model = new TableRowModel<>();
                model.setRowHeader(new TableRowHeaderModel("aa", "aa"));
                List<TableRowCellModel> childData = new ArrayList<>();
                childData.add(TableRowCellModel.createRiseCell("11"));
                childData.add(TableRowCellModel.createRiseCell("11%"));
                childData.add(TableRowCellModel.createRiseCell("11"));
                childData.add(TableRowCellModel.createRiseCell("1"));
                childData.add(TableRowCellModel.createFallCell("-11"));
                childData.add(TableRowCellModel.createFallCell("-11%"));
                model.setRowData(childData);
                mAdapter.notifyItemData(4, model);
            }
        }, 2000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
