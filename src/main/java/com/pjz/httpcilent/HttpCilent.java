package com.pjz.httpcilent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: HttpCilent
 * @Description HttpCilent 测试http接口类
 * @Author: pengjianzhong
 * @Date: 2019/8/4 9:33
 * @Version: 1.0
 **/
public class HttpCilent {

    /**
     * @Description GET方法
     * @Author pengjianzhong
     * @Param [url, params]
     * @Return java.lang.String
     * @Date 2019/8/4 10:36
     **/
    public static String doGet(String url, Map<String, Object> params) throws IOException {
        List<NameValuePair> getData = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            getData.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(getData, Consts.UTF_8));
        HttpGet httpGet = new HttpGet(url + "?" + paramStr);
        return execute(httpGet);

    }

    /**
     * @Description POST方法
     * @Author pengjianzhong
     * @Param [url, jsonParams]
     * @Return java.lang.String
     * @Date 2019/8/4 10:30
     **/
    public static String doPost(String url, String jsonParams) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        httpPost.addHeader(HTTP.CONTENT_TYPE, "text/json");
        httpPost.setEntity(new StringEntity(jsonParams));
        return execute(httpPost);

    }

    /**
     * @Description POST方法
     * @Author pengjianzhong
     * @Param [url, params]
     * @Return java.lang.String
     * @Date 2019/8/4 10:18
     **/
    public static String doPost(String url, Map<String, Object> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> postData = new ArrayList();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            postData.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(postData));
        return execute(httpPost);

    }

    /**
     * @Description 执行http调用
     * @Author pengjianzhong
     * @Param [httpUriRequest]
     * @Return java.lang.String
     * @Date 2019/8/4 9:59
     **/
    private static String execute(HttpUriRequest httpUriRequest) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpUriRequest);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.name());
            return result;
        }
        return null;

    }
}
