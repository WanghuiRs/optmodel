package com.shengao.optmodel.domain;

import java.util.List;

public class ProductionCapacity extends DataTable {

    private int tableHeaderNums = 6;

    private String factoryNo;
    private String factoryName;
    private String factoryLoca;
    private String maxLiquidCap;
    private String maxGrainCap;
    private String minGrainCap;

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryLoca() {
        return factoryLoca;
    }

    public void setFactoryLoca(String factoryLoca) {
        this.factoryLoca = factoryLoca;
    }

    public String getMaxLiquidCap() {
        return maxLiquidCap;
    }

    public void setMaxLiquidCap(String maxLiquidCap) {
        this.maxLiquidCap = maxLiquidCap;
    }

    public String getMaxGrainCap() {
        return maxGrainCap;
    }

    public void setMaxGrainCap(String maxGrainCap) {
        this.maxGrainCap = maxGrainCap;
    }

    public String getMinGrainCap() {
        return minGrainCap;
    }

    public void setMinGrainCap(String minGrainCap) {
        this.minGrainCap = minGrainCap;
    }

    @Override
    public int getTableHeaderNums() {
        return tableHeaderNums;
    }

    @Override
    public void setTableHeaderNums(int tableHeaderNums) {
        this.tableHeaderNums = tableHeaderNums;
    }

    @Override
    public void fillUp(List<String> strArr) {
        if(strArr.size() < tableHeaderNums){

        }
    }

    @Override
    public String toString() {
        return factoryNo + "@#@" +
                factoryName + "@#@" +
                factoryLoca + "@#@" +
                maxLiquidCap + "@#@" +
                maxGrainCap + "@#@" +
                minGrainCap + "@#@";
    }
}
