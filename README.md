# XTableView [![](https://jitpack.io/v/GitHubZJY/XTableView.svg)](https://jitpack.io/#GitHubZJY/XTableView)
一个基于RecyclerView+Scroller实现的二维表格组件，同时支持侧滑菜单、拖动调整列表顺序等拓展功能。
A two-dimensional table view, base on recyclerview, both support to side slide menu、drag item and more.

## 特性
1. 基于RecyclerView实现，可复用视图 <br/>
2. 支持自定义数据源类型、表头及单元格样式 <br/>
3. 支持列表Item侧滑菜单 <br/>
4. 支持拖拽变换顺序 <br/>
5. 支持AndroidX <br/>

## 效果预览
![](https://github.com/GitHubZJY/XTableView/blob/master/image/xtableview_1.jpg)

## 如何使用
在项目根目录的build.gradle添加：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在项目的build.gradle添加如下依赖：
```
implementation 'com.github.GitHubZJY:XTableView:v1.0.0'
```

### 1.在xml中引用XTableView

```xml
<com.zjy.xtableview.XTableView
        android:id="@+id/table_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:swipeLayout="@layout/table_swipe_menu_layout"
        app:headerHeight="50dp"
        app:rowHeight="80dp"
        app:cellWidth="130dp">

</com.zjy.xtableview.XTableView>
```

### 2.在代码中初始化XTableView
```java
ITableView vTableView = findViewById(R.id.table_view);
//设置是否支持长按拖动列表项
vTableView.setLongPressDragEnable(true);
//设置是否支持侧滑菜单
vTableView.setSwipeEnable(true);
```

### 3.绑定数据
绑定数据的设计灵感来自于RecyclerView的adapter设计，与数据相关的操作均通过 `XTableAdapter` 作为中间者来进行.
#### 1.自定义一个adapter继承于 `XTableAdapter` ，通过实现adapter的方法，自定义表格样式。`XTableAdapter` 的各个方法作用如下：
>**onBindHeader** 创建列头部视图 <br/>
**onCreateTableItem** 创建每一个单元格的视图 <br/>
**onBindTableItem** 绑定每一个单元格的视图数据 <br/>
**onCreateRowHeader** 创建每一行的头部视图 <br/>
**onBindRowHeader** 绑定每一行的头部视图数据

```java
public class CustomTableAdapter extends XTableAdapter<String, TableRowModel<TableRowHeaderModel, TableRowCellModel>>{

    public CustomTableAdapter(Context context) {
        super(context);
    }

    @Override
    public View onBindHeader(int position, String t) {
        TextView cellTv = new TextView(getContext());
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
        vTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        vDetail.setText(TextUtils.isEmpty(detail) ? "" : detail);
    }
}
```

#### 2.通过adapter的 `bindData` 绑定自定义数据源.

```java
/**
 * 更新表格数据
 * @param header 左上角的表头数据
 * @param columnHeader 列头部数据集合
 * @param tableData 每一行的数据集合
 */
public void bindData(String header, List<T> columnHeader, List<H> tableData)
```

#### 3.将adapter设置给XTableView.

```
vTableView.setTableAdapter(adapter);
```

### 4.数据变更时刷新视图.
数据变更也是通过adapter对象来进行：<br/>
如果是所有数据替换，可调用 `bindData` 方法设置新的数据，然后通过 `notifyDataSetChanged` 进行更新. <br/>
如果是单条数据源刷新，可调用 `notifyItemData(int position, H data)` 进行更新，position是对应的下标，data为新的数据.
&nbsp;
## 感谢与参考
[SwipeRecyclerView](https://github.com/yanzhenjie/SwipeRecyclerView) <br/>
侧滑和拖拽是在SwipeRecyclerView这个库的基础上修改，是一个基于RecyclerView拓展得不错的组件。本库的自定义样式和数据类型基于适配器的模式设计，后续会继续更新，提升组件的定制性和可拔插性.
