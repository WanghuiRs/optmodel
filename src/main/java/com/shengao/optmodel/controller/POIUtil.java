package com.shengao.optmodel.controller;

import com.shengao.optmodel.domain.OptResult;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.shengao.optmodel.controller.FileUtil.writeToFile;

/**
 * excel读写工具类 */
public class POIUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private static final String fileName[] = {
            "order_plan.txt",
            "factory_production_capacity.txt",
            "factory_packaging_capacity.txt",
            "verifiedFactory.txt",
            "factory_production_type.txt",
            "transfer_location.txt",
            "transfer_cost.txt",
            "factory_6PPD_cost.txt",
            "factory_6PPD_cost2.txt",
            "factory_safe_storage.txt",
            "transfer_constraint_route.txt",

    };
    //第一个数字代表该sheet页的表头占几行，第二个数字代表该sheet页有几个表
    private final static String[] configuration = {"3,1","2,2,15,3","2,1","2,1","3,1","2,1",
            "2,2,17,9", //暂时只读一个表格
            "2,1",
            "2,1",

    };


    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static String readExcel(MultipartFile file, String filePath) throws IOException {
        //检查文件
        String msg = checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);

        //固定sheet页的个数为 8
        int sheetNumber = 9;
        int fileNo = 0;
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < sheetNumber;sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                if(workbook.isSheetHidden(sheetNum)){
                    workbook.removeSheetAt(sheetNum);
                    sheetNum--;
                    continue;
                }

                if(sheetNum == 6) {
                    int a = 0;
                }

                String[] configArr = configuration[sheetNum].split(",");
                int sheetTableNum = Integer.parseInt(configArr[1]);

                //获得当前sheet的开始行
//                int firstRowNum  = sheet.getFirstRowNum();
                int firstRowNum = Integer.parseInt(configArr[0]);
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                if(sheetNum == 1) {
                    lastRowNum = firstRowNum + 3;
                }else if (sheetNum == 6){
                    lastRowNum = firstRowNum +9;
                }
                //循环除了第一行的所有
                for (int tableNum = 0; tableNum < sheetTableNum; tableNum++) {
                    //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
                    List<String[]> list = new ArrayList<String[]>();
                    if(tableNum == 1){
                        firstRowNum = Integer.parseInt(configArr[2]);
                        lastRowNum = firstRowNum + Integer.parseInt(configArr[3]);
                    }
                    for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                        //获得当前行
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum =  row.getLastCellNum();
//                        int lastCellNum = row.getPhysicalNumberOfCells();
                        if (firstCellNum == lastCellNum) continue;
                        String[] cells = new String[lastCellNum];
                        //循环当前行
                        for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                            Cell cell = row.getCell(cellNum);
                            cells[cellNum] = getCellValue(cell);
                        }
                        list.add(cells);
                    }
                    writeToFile(filePath + fileName[fileNo++], list);
                }
            }
            workbook.close();

        }
        return msg;
    }
    public static String checkFile(MultipartFile file) throws IOException{
        String msg = "upload success";
        //判断文件是否存在
        if(null == file){
            msg =  "文件不存在";
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            msg = "不是excel文件";
            throw new IOException(fileName + "不是excel文件");
        }
        return msg;
    }
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null ){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        /*
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        */
        //判断数据的类型
        switch (cell.getCellType()){
            //poi把日期数据也归类为 Cell.CELL_TYPE_NUMERIC 数字类型，

            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(value);
                    cellValue = sdf.format(date);
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String temp = cell.getStringCellValue();
                    if(temp.indexOf('.') != -1){
                        double tempNum = Double.parseDouble(temp);
                        cellValue = String.valueOf(new DecimalFormat("0.00").format(tempNum));
                    }else{
                        cellValue = temp;
                    }
                }
                break;


            /**
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;

            */
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                double temp = cell.getNumericCellValue();
                cellValue = String.valueOf(new DecimalFormat("0.00").format(temp));
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = " ";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }

        return cellValue;
    }

    /**
     * 生产计划及运输安排优化结果 的Excel表格导出
     * @param response HttpServletResponse对象
     * @param optResultList
     * @throws IOException 抛IO异常
     */

    public static void exportExcel(HttpServletResponse response,
                                   List<OptResult> optResultList) throws IOException {

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建sheet页
        HSSFSheet sheet = workbook.createSheet("生产计划及运输安排优化结果");
        int rowNum = 0;
        HSSFRow headerRow = sheet.createRow(rowNum++);
        String[] header = OptResult.getTableHeaders();
        for (int i = 0; i < header.length; i++){
            HSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(header[i]);
        }
        if (optResultList != null && optResultList.size()>0){
            for(OptResult optResult : optResultList) {
                List<String> tableData = optResult.fillUpAsList();
                HSSFRow row = sheet.createRow(rowNum++);
                for (int i = 0; i < header.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(tableData.get(i));
                }
            }
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + "生产计划及运输安排优化结果");

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());

        //关闭workbook
        workbook.close();
    }

}