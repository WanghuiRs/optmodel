package com.shengao.optmodel.controller;

import com.mysql.cj.xdevapi.JsonArray;
import com.shengao.optmodel.domain.OptResult;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.shengao.optmodel.controller.FileUtil.readFromFile;
import static com.shengao.optmodel.controller.POIUtil.exportExcel;
import static com.shengao.optmodel.controller.POIUtil.readExcel;

@Controller
public class ExcelController {

    private static final String[] sheetNames = {"工厂基本信息-产能","工厂基本信息-包装能力","工厂基本信息-产品品种","工厂基本信息-6PPD生产成本","工厂基本信息-产品安全库存",
            "物流基本信息-运输配送报价","物流基本信息-固定路线约束","物流基本信息-中转仓及客户分布","订单计划"};

    private static final String[] sheetHeader =
            {"工厂编码,工厂名称,工厂位置,工厂仓库容量,工厂最大可用产能（液体）,工厂最大可用产能（颗粒）",
                    "工厂编码,工厂名称,工厂位置,工厂最大包装能力(分SKU大类)",
                    "工厂编码,工厂名称,产品编码,产品名称,SKU类型名称",
                    "工厂编码,工厂名称,产品类型,综合单位成本(元/吨),日期(年月),MIBK-非保税,铂碳催化剂（干剂）,氢气-保税,氢气-非保税,油炉,蒸汽,自来水,4-ADPA（散水）,车间电,分摊电,本部门-人工,本部门-其他,本部门-折旧,本部门-包装费,本部门-维修,间接部门-人工,间接部门-其他,间接部门-折旧,间接部门-包装费,间接部门-维修",
                    "工厂编码,工厂名称,工厂产成品安全库存(分到SKU大类),期初库存,日期(年月)",
                    "工厂编码,工厂名称,客户名称,客户送达点省,客户送达点市区,客户送达点区/县,物流承运商,报价有效年月,阶梯报价1,阶梯报价2,阶梯报价3,阶梯报价4,阶梯报价5,阶梯报价6",
                    "约束路线编码,约束路线",
                    "客户/中转仓编码,客户/中转仓名称,客户送达地/中转仓所在国家,客户送达地/中转仓所在省,客户送达地/中转仓所在所在市,客户送达地/中转仓所在县,备用字段",
                    "客户编号,客户名称,认证工厂,库存地点,SKU(25KG、50KG、吨等),订单发货量(吨)-可拆分,交付日期(年月),客户要求送达地,是否保税,内外贸(预留字段)",
            };

    @RequestMapping(value = "/excelUpload", method = RequestMethod.POST)
    public @ResponseBody
    String uploadExcel(@RequestParam("file") MultipartFile file,
                       HttpServletRequest request) throws IOException {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/

        //String filePath = request.getSession().getServletContext().getRealPath("myFileTargetFolder/");
        try {
            String filePath = "/Users/wanghui/shengaohuaxue/myFileFolder/";
            String msg = readExcel(file, filePath);
            System.out.println(filePath);
//          FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "{\"data\":\"文件上传失败,请下载正确excel模版！\"}";
        }
        //返回json
        return "{\"data\":\"文件上传成功\"}";
    }

    /**
    @RequestMapping(value = "/excelDownload", method = RequestMethod.GET)
    public @ResponseBody
    void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> exceldata = new ArrayList<String>();
        String fileName = "模型基础数据模版";
        exportExcel(response,null,sheetNames,fileName, sheetHeader, 0);
    }
*/

    @RequestMapping(value = "/excelDownload", method = RequestMethod.GET)
    public @ResponseBody
    void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String filePath = "./target/classes/static/圣奥化学数据模版.xlsx";

        // 设置输出流
        String contentType = "application/octet-stream";
        try {

            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            File excelFile =new File(filePath);
            long fileLength = excelFile.length();

            response.setContentType(contentType);
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(excelFile.getName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(filePath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            return  ;
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
    }

    /**
     * 导出生产计划及配送安排的优化结果
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportOptResult", method = RequestMethod.GET)
    public @ResponseBody
    void exportOptResult(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<OptResult> optResultList = new ArrayList<OptResult>();
        //从 txt 中获取数据
        String filePath = "/Users/wanghui/shengaohuaxue/myFileFolder/optResult.txt";
        JSONArray jarray = readFromFile(filePath, OptResult.names);
        optResultList = (List<OptResult>) JSONArray.toArray(jarray);
        try{
            exportExcel(response, optResultList);
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
    }

}
