package com.shengao.optmodel.domain;

import java.util.ArrayList;
import java.util.List;

public class OptResult extends DataTable{

    //发货计划单	客户	产品类型SKU	执行生产工厂	生产量	运输路线	综合成本(生产成本+物流成本)	月份
    public static final String[] tableHeaders = {"发货计划单","客户","产品类型SKU","执行生产工厂","生产量","运输路线","综合成本(生产成本+物流成本)","月份"};
    public static final String[] names = {"orderNo","customerName","skuNo","factoryName","amount","transferRoute","productionCost","transferCost","date"};
    public static final String[] returnNames = {"orderNo","customerName","skuNo","factoryName","amount","transferRoute","productionCost","transferCost","date"};
    private int tableHeaderNums = 8;

    private String orderNo;
    private String customerName;
    private String skuNo;
    private String factoryName;
    private String amount;
    private String transferRoute;
    private String productionCost;
    private String transferCost;
    private int cost;
    private String terminalCost;
    private String date;

    public static String[] getTableHeaders() {
        return tableHeaders;
    }

    @Override
    public int getTableHeaderNums() {
        return tableHeaderNums;
    }

    @Override
    public void setTableHeaderNums(int tableHeaderNums) {
        this.tableHeaderNums = tableHeaderNums;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransferRoute() {
        return transferRoute;
    }

    public void setTransferRoute(String transferRoute) {
        this.transferRoute = transferRoute;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductionCost() {
        return productionCost;
    }

    public String getTransferCost() {
        return transferCost;
    }

    public void setProductionCost(String productionCost) {
        this.productionCost = productionCost;
    }

    public void setTransferCost(String transferCost) {
        this.transferCost = transferCost;
    }

    public String getTerminalCost() {
        return this.cost + "("+ this.productionCost + "+" +this.transferCost +")";
    }

    public void setTerminalCost(String terminalCost) {
        this.terminalCost = terminalCost;
    }

    @Override
    public void fillUp(List<String> strArr) {

    }

    public List<String> fillUpAsList() {
        List<String> list = new ArrayList<String >();

        list.add(this.getOrderNo());
        list.add(this.getCustomerName());
        list.add(this.getSkuNo());
        list.add(this.getFactoryName());
        list.add(this.getAmount());
        list.add(this.getTransferRoute());
        list.add(String.valueOf(this.getCost()));
        list.add(this.getDate());

        return list;
    }
}
