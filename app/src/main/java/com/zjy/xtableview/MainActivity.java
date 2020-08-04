package com.zjy.xtableview;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zjy.xtableview.model.TableHeaderModel;
import com.zjy.xtableview.model.TableItemCellModel;
import com.zjy.xtableview.model.TableItemModel;
import com.zjy.xtableview.widget.item.TableCellAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TableItemModel> mDataList;
    private TableHeaderModel mHeaderModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        final ITableView vTableView = findViewById(R.id.table_view);
        vTableView.setCellAdapter(new TableCellAdapter<TextView>() {

            @Override
            public TextView getView(int position, TableItemCellModel cellModel, ViewGroup parent) {
                TextView cellTv = new TextView(MainActivity.this);
                cellTv.setTextColor(cellModel.isRise() ?
                        getResources().getColor(R.color.table_view_rise_txt_color)
                        : getResources().getColor(R.color.table_view_fall_txt_color));
                cellTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                cellTv.setGravity(Gravity.CENTER);
                cellTv.setText(cellModel.getContent());
                return cellTv;
            }

            @Override
            public void bindData(int position, TableItemCellModel cellModel, TextView itemView) {
                itemView.setTextColor(cellModel.isRise() ?
                        getResources().getColor(R.color.table_view_rise_txt_color)
                        : getResources().getColor(R.color.table_view_fall_txt_color));
                itemView.setText(cellModel.getContent());
            }
        });
        vTableView.bindData(mHeaderModel, mDataList);
        vTableView.setLongPressDragEnable(true);
        vTableView.setSwipeEnable(true);
        vTableView.setTableItemClickListener(new TableItemClickListener() {
            @Override
            public void clickCell(TableItemCellModel cellModel) {
                Toast.makeText(MainActivity.this, cellModel.getContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clickHeaderCell(TableHeaderModel headerModel) {

            }
        });
    }

    private void initData() {
        mDataList = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            final TableItemModel model = new TableItemModel();
            model.setTitle("横向标题1");
            model.setDetail("横向描述1");
            List<TableItemCellModel> childData = new ArrayList<>();
            childData.add(TableItemCellModel.createRiseCell("123"));
            childData.add(TableItemCellModel.createRiseCell("15%"));
            childData.add(TableItemCellModel.createRiseCell("22"));
            childData.add(TableItemCellModel.createRiseCell("112"));
            childData.add(TableItemCellModel.createFallCell("-123"));
            childData.add(TableItemCellModel.createFallCell("-99%"));
            model.setDataList(childData);
            mDataList.add(model);

            final TableItemModel model2 = new TableItemModel();
            model2.setTitle("横向标题2");
            model2.setDetail("横向描述2");
            List<TableItemCellModel> childData2 = new ArrayList<>();
            childData2.add(TableItemCellModel.createRiseCell("13"));
            childData2.add(TableItemCellModel.createFallCell("-20%"));
            childData2.add(TableItemCellModel.createRiseCell("12"));
            childData2.add(TableItemCellModel.createRiseCell("10"));
            childData2.add(TableItemCellModel.createRiseCell("057"));
            childData2.add(TableItemCellModel.createRiseCell("62%"));
            model2.setDataList(childData2);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
