package com.zjy.xtableview;

import com.zjy.xtableview.model.TableHeaderModel;
import com.zjy.xtableview.model.TableItemCellModel;

/**
 * Date: 2020/7/21
 * Author: Yang
 * Describe: 单元格点击接口
 */
public interface TableItemClickListener {

    void clickCell(TableItemCellModel cellModel);

    void clickHeaderCell(TableHeaderModel headerModel);
}
