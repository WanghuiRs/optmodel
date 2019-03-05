package com.shengao.optmodel.controller;

import com.mysql.cj.xdevapi.JsonArray;
import com.shengao.optmodel.domain.OptResult;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.shengao.optmodel.controller.FileUtil.readFromFile;
import static com.shengao.optmodel.controller.FileUtil.writeToFile;

@RestController
public class DataController {

    private static final String[] filePath = {
            "/Users/wanghui/shengaohuaxue/myFileFolder/factory_production_capacity.txt", //工厂产能
            "/Users/wanghui/shengaohuaxue/myFileFolder/factory_packaging_capacity.txt",  //工厂包装能力
            "/Users/wanghui/shengaohuaxue/myFileFolder/factory_production_type.txt",     //工厂可生产产品种类
            "/Users/wanghui/shengaohuaxue/myFileFolder/factory_6PPD_cost.txt",           //工厂6PPD生产成本1
            "/Users/wanghui/shengaohuaxue/myFileFolder/factory_safe_storage.txt",        //工厂安全库存
            "/Users/wanghui/shengaohuaxue/myFileFolder/transfer_cost.txt",               //运输报价
            "/Users/wanghui/shengaohuaxue/myFileFolder/transfer_constraint_route.txt",   //运输路线约束
            "/Users/wanghui/shengaohuaxue/myFileFolder/transfer_location.txt",           //中转仓和客户位置
            "/Users/wanghui/shengaohuaxue/myFileFolder/order_plan.txt",                  //订单计划
            "/Users/wanghui/shengaohuaxue/myFileFolder/verifiedFactory.txt",              // 9-认证工厂
            "/Users/wanghui/shengaohuaxue/myFileFolder/factory_6PPD_cost2.txt",          // 10-工厂6PPD生产成本2
            "/Users/wanghui/shengaohuaxue/myFileFolder/optimal_result.txt",               //优化结果
    };

    private static final String[][] tableHeader =
            {
                    // 0-工厂基本信息产能
                    {"factoryNo","factoryName","factoryLoca","maxLiquidCap","maxGrainCap","minGrainCap"},
//                    {"factoryNo","factoryName","factoryLoca","skuType","packageCapacity"}
                    // 1-工厂基本信息-包装能力
                    {"factoryNo","factoryName","factoryLoca","packageCapacity"},
                    // 2-工厂基本信息-产品品种
                    {"factoryNo","factoryName","productionNo","productionName","skuNo"},
                    // 3-工厂基本信息-成本1
                    {"factoryNo","factoryName","productionType","unitCost","date", "MIBK","activatorGC1801","activatorCB5","activatorCB10",
                            "activatorTanBo","h2_fbs","youlu","zhengqi","zilaishui","4_adpa_wg","4_adpa", "chejiandian","fentandian", "bbm-rg",
                            "bbm-qt", "bbm-zj","bbm-bzf","bbm-wx","jjbm-rg","jjbm-qt","jjbm-zj","jjbm-bzf","jjbm-wx" },
                    // 4-工厂基本信息-安全库存
                    {"factoryNo","factoryName","safeStorage","weizhi","dateStorage", "date"},
                    // 5-运输基本信息-运输配送报价
                    {"factoryNo","factoryName","customerName","customerProvince","customerCity","customerArea","transferProvider",
                            "effectiveDate","ladderPrice1","ladderPrice2","ladderPrice3","ladderPrice4","ladderPrice5",
                            "ladderPrice6"},
                    // 6-运输基本信息-约束路线
                    {"constraintRouteNo","constraintRoute"},
                    // 7-运输基本信息-客户位置
                    {"transferNo","transferName","transferCountry","transferProvince","transferCity","transferArea","AlternateField"},
                    // 8-计划订单
                    {"customerNo","customerName","verifyFactory","stockLocation","skuNo","productionName","orderAmount","DeliveryDate","destination","isBonded","tradeType","sendProductionNo","saleGroup"},
                    // 9-认证工厂
                    {"factoryNo","factoryName","acceptCustomerNo","acceptCustomerName","reserved"},
                    // 10-工厂基本信息-成本2
                    {"factoryNo","factoryName","productionType","unitCost","date", "MIBK","activatorGC1801","activatorCB5","activatorCB10",
                            "activatorTanBo","h2_fbs","youlu","zhengqi","zilaishui","4_adpa_wg","4_adpa", "chejiandian","fentandian", "bbm-rg",
                            "bbm-qt", "bbm-zj","bbm-bzf","bbm-wx","jjbm-rg","jjbm-qt","jjbm-zj","jjbm-bzf","jjbm-wx" },
                    // 11-优化结果
                    {"orderNo","customerName","skuNo","factoryName","amount","transferRoute","productionCost","transferCost","date"}

            };


    //工厂基本信息-产能
    @RequestMapping("/getProCapacityData")
    public JSONArray getProCapacityData() throws Exception{
        String specificFilePath = filePath[0];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[0];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }

    //工厂基本信息-包装能力
    @RequestMapping("/getPackageCapacityTableData")
    public JSONArray getPackageCapacityTableData() throws Exception{
        String specificFilePath = filePath[1];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[1];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }


    //客户认证工厂
    @RequestMapping("/getCustomerVerifiedFactory")
    public JSONArray getCustomerVerifiedFactory() throws Exception{
        String specificFilePath = filePath[9];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[9];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }


    //工厂基本信息-可生产产品品种
    @RequestMapping("/getProductionTypeTableData")
    public JSONArray getProductionTypeTableData() throws Exception{
        String specificFilePath = filePath[2];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[2];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }


    //工厂基本信息-6PPD生产成本
    @RequestMapping("/get6ppdCost1TableData")
    public JSONArray get6ppdCost1TableData() throws Exception{
        String specificFilePath = filePath[3];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[3];
        jarray = readFromFile(specificFilePath, array);
        //System.out.println(jarray);
        return jarray;
    }

    //工厂基本信息-6PPD生产成本
    @RequestMapping("/get6ppdCost2TableData")
    public JSONArray get6ppdCost2TableData() throws Exception{
        String specificFilePath = filePath[10];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[10];
        jarray = readFromFile(specificFilePath, array);
        //System.out.println(jarray);
        return jarray;
    }

    //工厂基本信息-产品安全库存
    @RequestMapping("/getSafeStorageData")
    public JSONArray getSafeStorageData() throws Exception{
        String specificFilePath = filePath[4];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[4];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }

    //物流基本信息-运输配送报价
    @RequestMapping("/getTransCostTableData")
    public JSONArray getTransCostTableData() throws Exception{
        String specificFilePath = filePath[5];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[5];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }

    //物流基本信息-固定约束线路
    @RequestMapping("/getConsRouteTableData")
    public JSONArray getConsRouteTableData() throws Exception{
        String specificFilePath = filePath[6];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[6];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }

    //物流基本信息-中转仓和客户点分布
    @RequestMapping("/getLocationTableData")
    public JSONArray getLocationTableData() throws Exception{
        String specificFilePath = filePath[7];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[7];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }

    //订单计划
    @RequestMapping("/getOrderPlanTableData")
    public JSONArray getOrderPlanTableData() throws Exception{
        String specificFilePath = filePath[8];
        JSONArray jarray = new JSONArray();
        String[] array = tableHeader[8];
        jarray = readFromFile(specificFilePath, array);
        return jarray;
    }

    //生产运输安排优化结果
    @RequestMapping("/getOptResultTableData")
    public JSONArray getOptResultTableData() throws Exception{
        List<OptResult> optResultList = new ArrayList<OptResult>();
        try{

            //从 txt 中获取数据
            String filePath = "/Users/wanghui/shengaohuaxue/myFileFolder/optResult.txt";
            JSONArray jarray = readFromFile(filePath, tableHeader[11]);
            optResultList = (List<OptResult>) JSONArray.toList(jarray, new OptResult(), new JsonConfig());
            for(OptResult optResult : optResultList) {
                int cost1 = Integer.parseInt(optResult.getProductionCost());
                int cost2 = Integer.parseInt(optResult.getTransferCost());
                optResult.setCost(cost1+cost2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSONArray.fromObject(optResultList);
    }

    @RequestMapping(value = "/edit6PPDCost1Data",method = RequestMethod.POST)
    public JSONObject edit6PPDCost1Data(@RequestParam(value = "editData[]") String[] editData) throws Exception{
        String specificFilePath = filePath[3];
        String[] array1 = tableHeader[3];

        List<String[]> data = new ArrayList<String[]>();

        for(String str : editData) {
            String[] arr = str.split(",");
            data.add(arr);
        }
        writeToFile(specificFilePath, data);

        return JSONObject.fromObject("{'msg':'6PPD成本修改成功!'}");
    }

    @RequestMapping(value = "/edit6PPDCost2Data",method = RequestMethod.POST)
    public JSONObject edit6PPDCost2Data(@RequestParam(value = "editData[]") String[] editData) throws Exception{
        String specificFilePath = filePath[10];
        String[] array1 = tableHeader[10];

        List<String[]> data = new ArrayList<String[]>();

        for(String str : editData) {
            String[] arr = str.split(",");
            data.add(arr);
        }
        writeToFile(specificFilePath, data);

        return JSONObject.fromObject("{'msg':'6PPD成本修改成功!'}");
    }


}