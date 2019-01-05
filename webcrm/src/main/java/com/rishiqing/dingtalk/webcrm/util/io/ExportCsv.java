package com.rishiqing.dingtalk.webcrm.util.io;

import com.rishiqing.common.log.LogFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: Yin Jian
 * @create: 2019-01-05 10:39
 */
public class ExportCsv {
    private static final Logger bizLogger = LoggerFactory.getLogger(ExportCsv.class);

    /**
     * io流输出Csv格式
     *
     * @param response
     * @param tempFile
     * @throws Exception
     */
    public static void outPutCVS(HttpServletResponse response, File tempFile) {
        FileInputStream in = null;
        ServletOutputStream outputStream = null;
        //输出
        try {
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            File fileLoad = new File(tempFile.getCanonicalPath());
            response.reset();
            response.setContentType("application/csv");
            response.setHeader("content-disposition", "attachment; filename=corp.csv");
            long fileLength = fileLoad.length();
            String length1 = String.valueOf(fileLength);
            response.setHeader("Content_Length", length1);
            in = new java.io.FileInputStream(fileLoad);
            //将要输出的内容设置BOM标识(以 EF BB BF 开头的字节流)
            outputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            int n;
            while ((n = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, n); //每次写入out1024字节
            }

        } catch (Exception e) {
            bizLogger.error(LogFormatter.format(
                    LogFormatter.LogEvent.EXCEPTION,
                    "outPutCVS",
                    LogFormatter.getKV("response", response),
                    LogFormatter.getKV("tempFile", tempFile)
            ), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                bizLogger.error(LogFormatter.format(
                        LogFormatter.LogEvent.EXCEPTION,
                        "io close",
                        LogFormatter.getKV("in", in),
                        LogFormatter.getKV("outputStream", outputStream)
                ), e);
            }
        }

    }
}
