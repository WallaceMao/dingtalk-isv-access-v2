package com.rishiqing.dingtalk.biz.http;

import com.rishiqing.dingtalk.biz.exception.HttpRequestException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * http请求的包装类
 * @author Wallace Mao
 * Date: 2018-11-03 17:56
 */
public class HttpRequestClient {
    private Integer connectionRequestTimeout = 3000;
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

    public String doHttpGet(String url) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeOut).build();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        String result;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpRequestException(
                        response.getStatusLine().getStatusCode(),
                        "HTTP GET, url: " + url
                );
            }
            result =  EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            throw new HttpRequestException("HTTP GET, httpClient exception, url: " + url, e);
        } finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }

    public String doHttpPost(String url, String content, Map headers) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        if(headers != null){
            for(Object key : headers.keySet()){
                httpPost.addHeader((String)key, (String)headers.get(key));
            }
        }
        StringEntity requestEntity = new StringEntity(content, "utf-8");
        httpPost.setEntity(requestEntity);
        String result;
        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpRequestException(
                        response.getStatusLine().getStatusCode(),
                        "HTTP POST, url: " + url + ", content: " + content
                );
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            throw new HttpRequestException("HTTP POST, httpClient exception, url: " + url + ", content: " + content, e);
        }finally {
            IOUtils.closeQuietly(response);
        }
        return result;
    }
}
