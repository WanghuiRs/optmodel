package com.shengao.optmodel.controller;

import com.shengao.optmodel.domain.DataTable;
import com.shengao.optmodel.domain.OptResult;
import com.shengao.optmodel.domain.ProductionCapacity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {



    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void readExcel(String filePath){

    }

    public static void writeToFile(String filePath, List<String[]> list) {
        String os = System.getProperty("os.name");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for(String[] arr : list) {
                StringBuffer contentRow = new StringBuffer();
                for(int j = 0; j<arr.length; j++) {
                    contentRow.append(arr[j] + "@#@");
                }
                if(os.toLowerCase().startsWith("win")){
                    contentRow.append("\r\n"); //window \r\n
                }else{
                    contentRow.append("\n"); //unix \r\n
                }

                bw.write(contentRow.toString());
            }
            bw.close();
            fw.close();
            System.out.println(filePath + "--test done!");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static JSONArray readFromFile(String path, String[] tableHead) {
        JSONArray jarray = new JSONArray();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                //读出数据并处理

//              int len = tableHead.length < strArr.length? tableHead.length:strArr.length;
                int len = tableHead.length;
                String[] strArr = new String[len];
                String[] temp = str.split("@#@");
                for(int i = 0; i < len; i++) {
                    if(i<temp.length)
                        strArr[i] = temp[i];
                    else
                        strArr[i] = "";
                }

                JSONObject ob = new JSONObject();

                for (int i = 0; i < len ; i++) {
                    ob.put(tableHead[i], strArr[i]);
                }
                jarray.add(ob);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jarray;
    }

    public static List<OptResult> readOptResultFromFile(String path, String[] tableHead) {
        List<OptResult> optResultList = new ArrayList<OptResult>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                //读出数据并处理

//
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optResultList;
    }
}

