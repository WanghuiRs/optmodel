package com.shengao.optmodel.controller;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static com.shengao.optmodel.controller.FileUtil.readFromFile;

@Controller

public class CoreController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }



    @RequestMapping("/home")
    public String home() {
        return "index";
    }

    @RequestMapping("/proCapacity")
    public String proCapacityView() {
        return "productionCapacity";
    }

    @RequestMapping("/pacCapacity")
    public String pacCapacityView() {
        return "packageCapacity";
    }

    @RequestMapping("/proType")
    public String proTypeView() {
        return "productionType";
    }

    @RequestMapping("/page6PPDCost")
    public String page6PPDCostView() {
        return "6PPDCost";
    }


    @RequestMapping("/page6PPDCost2")
    public String page6PPDCostView2() {
        return "6PPDCost2";
    }

    @RequestMapping("/safeStock")
    public String safeStockView() {
        return "safeStorage";
    }

    @RequestMapping("/transCost")
    public String transCostView() {
        return "translateCost";
    }

    @RequestMapping("/conRout")
    public String conRoutView() {
        return "constrainRoute";
    }

    @RequestMapping("/location")
    public String pageLocationView() {
        return "location";
    }

    @RequestMapping("/preOrder")
    public String preOrderView() {
        return "orderPlan";
    }

    @RequestMapping("/result")
    public String resultView() {
        return "optResult";
    }

    @RequestMapping("/verifiedFactory")
    public String verifiedFactory() {
        return "customerVerifyFactory";
    }

}
