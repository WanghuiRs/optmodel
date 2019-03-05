package com.shengao.optmodel.domain;

import java.util.List;

public abstract class DataTable {

    //表头的个数
    private int tableHeaderNums;


    public abstract void fillUp(List<String> strArr);

    public int getTableHeaderNums() {
        return tableHeaderNums;
    }

    public void setTableHeaderNums(int tableHeaderNums) {
        this.tableHeaderNums = tableHeaderNums;
    }
}
