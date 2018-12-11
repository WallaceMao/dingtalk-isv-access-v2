package com.rishiqing.dingtalk.biz.http;

import com.rishiqing.dingtalk.biz.exception.HttpRequestException;
import com.rishiqing.dingtalk.biz.util.LogFormatter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.rishiqing.dingtalk.biz.util.LogFormatter.getKV;

/**
 * http请求的包装类
 *
 * @author Wallace Mao
 * Date: 2018-11-03 17:56
 */
public class HttpRequestClient {
    private static final Logger bizLogger = LoggerFactory.getLogger(HttpRequestClient.class);

    private Integer connectionRequestTimeout = 10000;
    private Integer socketTimeOut = 3000;
    private Integer connectTimeout = 3000;

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String httpPostJson(String url, String jsonContent) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return this.doHttpPost(url, jsonContent, headers);
    }

    public String httpPostJson(String url, String jsonContent, Map<String, String> headers) {
        headers.put("Content-Type", "application/json");
        return this.doHttpPost(url, jsonContent, headers);
    }

    public String httpPutJson(String url, String jsonContent, Map<String, String> headers) {
        headers.put("Content-Type", "application/json");
        return this.doHttpPut(url, jsonContent, headers);
    }

    public String doHttpGet(String url) {
        bizLogger.debug(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "begin http GET",
                getKV("url", url)
        ));
        HttpGet httpGet = new HttpGet(url);
        return doHttpRequest(httpGet, url, null);
    }

    public String doHttpGet(String url, Map<String, String> headers) {
        bizLogger.debug(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "begin http GET",
                getKV("url", url),
                getKV("headers", headers)
        ));
        HttpGet httpGet = new HttpGet(url);
        return doHttpRequest(httpGet, url, headers);
    }

    public String doHttpPost(String url, String content, Map<String, String> headers) {
        bizLogger.debug(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "begin http POST",
                getKV("url", url),
                getKV("content", content),
                getKV("headers", headers)
        ));
        HttpPost httpPost = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(content, "utf-8");
        httpPost.setEntity(requestEntity);
        return doHttpRequest(httpPost, url, headers);
    }

    public String doHttpPut(String url, String content, Map<String, String> headers) {
        bizLogger.debug(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "begin http PUT",
                getKV("url", url),
                getKV("content", content),
                getKV("headers", headers)
        ));
        HttpPut httpPut = new HttpPut(url);
        StringEntity requestEntity = new StringEntity(content, "utf-8");
        httpPut.setEntity(requestEntity);
        return doHttpRequest(httpPut, url, headers);
    }

    public String doHttpDelete(String url, Map<String, String> headers) {
        bizLogger.debug(LogFormatter.format(
                LogFormatter.LogEvent.START,
                "begin http DELETE",
                getKV("url", url),
                getKV("headers", headers)
        ));
        HttpDelete httpDelete = new HttpDelete(url);
        return doHttpRequest(httpDelete, url, headers);
    }

    private String doHttpRequest(HttpRequestBase request, String url, Map<String, String> headers) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeOut).build();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        request.setConfig(requestConfig);

        if (headers != null) {
            for (Object key : headers.keySet()) {
                request.addHeader((String) key, headers.get(key));
            }
        }
        CloseableHttpResponse response = null;
        String result;
        try {
            response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpRequestException(
                        response.getStatusLine().getStatusCode() + "HTTP request, url: " + request.getMethod() + " " + url
                );
            }
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            throw new HttpRequestException("HTTP Request, httpClient exception, url: " + url, e);
        } finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }
}
