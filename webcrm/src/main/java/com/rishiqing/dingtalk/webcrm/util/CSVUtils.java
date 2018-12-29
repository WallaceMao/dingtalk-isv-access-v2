package com.rishiqing.dingtalk.webcrm.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVUtils {

    public static String getBOM() {
        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
        return new String(b);
    }
    /**
     *
     *              导出生成csv文件
     * @author      ccg
     * @param       titles       标题头
     * @param       propertys    每一列标题头对应数据集合里对象的属性
     * @param       list         数据集合
     * @param       fileName     文件名称注意不能有空格以及冒号
     * @param       request
     * @param       response
     * @return
     * @throws      IOException
     * @throws      IllegalArgumentException
     * @throws      IllegalAccessException
     * Created      2017年2月22日 下午8:35:57
     */
    public static String exportCsv(
            String[] heads,
            String[] propertys,
            List<Object> list,
            String fileName,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, IllegalArgumentException{
        BufferedWriter bw = null;

        /*response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(fileName,"UTF-8"));
        //构建输出流，同时指定编码
        CsvWriter csvWriter=new CsvWriter("D:/",',', Charset.forName("utf-8"));
        *//*写头部*//*
        csvWriter.writeRecord(heads);
        //写内容
        for(Object obj : list){
            //写完一行换行
            bw.write("\r\n");
        }*/
        bw.flush();
        bw.close();
        return "0";
    }

    public static File createCSVFile(List exportData, LinkedHashMap map, String outPutPath,
                                     String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //定义文件名格式并创建
            csvFile =new File(outPutPath+fileName+".csv");
            file.createNewFile();
            // UTF-8使正确读取分隔符","
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);

            //写入前段字节流，防止乱码
            csvFileOutputStream.write(getBOM());
            // 写入文件头部
            for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
                csvFileOutputStream.write((String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "" );
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
                Object row = (Object) iterator.next();
                for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
                        .hasNext();) {
                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
                            .next();
                    String str=row!=null?((String)((Map)row).get( propertyEntry.getKey())):"";

                    if(StringUtils.isEmpty(str)){
                        str="";
                    }else{
                        str=str.replaceAll("\"","\"\"");
                        if(str.indexOf(",")>=0){
                            str="\""+str+"\"";
                        }
                    }
                    csvFileOutputStream.write(str);
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
}
