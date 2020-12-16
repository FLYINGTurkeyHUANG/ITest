package com.bj58.hds.wuleitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        String[] province = {"山西","辽宁","山东","河南","湖北","湖南","贵州","陕西","甘肃","青海","河北","吉林","黑龙江","江苏","浙江","内蒙"};
        String[] province = {"江苏","浙江","内蒙"};
        for(int i=0;i<province.length;i++){
            System.out.println(province[i]);
            String path = "D:/"+province[i];
            File file = new File(path);
            if(!file.exists()){
                file.mkdir();
            }
            //输入所在省
            String region = province[i];
            String url = "http://api.map.baidu.com/place/v2/search?query=瓷砖&region=";
            String url2 = "&output=json&ak=tzMSlBEtwfTGkz1kXOLX7aYHqKF3v1cS&scope=2&page_size=20&page_num=";
            //通过所在省获取省内所有的市级单位"通过市级进行搜索获取结果。
            //循环市列表获取市单位下的关键词数据
            List<String> cityList = getCity(url+region+url2);
            System.out.println("未去重前的城市数量="+cityList.size());
            //城市列表去重
            HashSet<String> set = new HashSet<>(cityList);

            for(String s:set){
                System.out.println(s);
            }
            System.out.println("去重后的城市数量="+set.size());
            //测试数据
//        List<String> cityList = new ArrayList<>();
//        cityList.add("抚州市");
//        cityList.add("南昌市");
//        cityList.add("鹰潭市");
//        cityList.add("上饶市");
//        cityList.add("乐平市");

            for(String city:set){
                System.out.println(city);
                getData(url+city+url2,city,province[i]);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }




    }


    /**
     * 获取省下属的市
     * */

    public static List<String> getCity(String url){
        List<String> cityList = new ArrayList<>();
        int page_num = 0;
        try {
            String response = httpGet(url+page_num);
            JSONObject res = JSON.parseObject(response);

            while(res.get("results") != null && !"[]".equals(res.get("results").toString())){
                if((int) res.get("status") == 0){
                    JSONArray itemArray = JSON.parseArray(res.get("results").toString());
                    for(Object item:itemArray){
                        JSONObject poi = JSON.parseObject(item.toString());
                        cityList.add(poi.get("name").toString());
                    }
                }
                System.out.println(page_num+"..");
                page_num++;
                response = httpGet(url+page_num);
                res = JSON.parseObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }
    /**
     * 获取结果数据
     * */

    public static void getData(String url,String city,String province){
        JSONArray array = new JSONArray();
        int page_num = 0;
        try {
            String response = httpGet(url+page_num);
            JSONObject res = JSON.parseObject(response);
            while(true){
                if((int) res.get("status") == 0){
                    JSONArray itemArray = JSON.parseArray(res.get("results").toString());
                    if(itemArray.size() == 0){
                        break;
                    }
//              createExcel(itemArray,page_num);
                    array.addAll(itemArray);
//                    打印代码
//                    for(Object item:itemArray){
//                        JSONObject poi = JSON.parseObject(item.toString());
//                        System.out.println(poi.get("name"));
//                        System.out.println(poi.get("address"));
//                        System.out.println(poi.get("telephone"));
//                        System.out.println("------------");
//                    }
               }
                Thread.sleep(2000);
                page_num++;
                response = httpGet(url+page_num);
                res = JSON.parseObject(response);
            }
            createExcel(array,city,province);
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 请求百度地图api获取检索结果
     * */
    public static String httpGet(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 生成Excel表格
     * */
    public static void createExcel(JSONArray array,String city,String province) {
        // 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet(city+"检索结果");
        // 添加表头行
        HSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 添加表头内容
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("名称");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("电话");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("地址");
        headCell.setCellStyle(cellStyle);
        int i= 0;
        // 添加数据内容
        for (Object item:array) {
            JSONObject poi = JSON.parseObject(item.toString());
            if(poi.get("telephone")==null){
                continue;
            }
            hssfRow = sheet.createRow(++i);
            // 创建单元格"并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(poi.get("name").toString());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(poi.get("telephone").toString());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(2);
            cell.setCellValue(poi.get("address").toString());
            cell.setCellStyle(cellStyle);


        }

        // 保存Excel文件
        try {
            OutputStream outputStream = new FileOutputStream("D:/"+province+"/"+city+".xls");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
