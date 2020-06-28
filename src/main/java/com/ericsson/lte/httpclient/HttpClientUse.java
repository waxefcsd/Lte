package com.ericsson.lte.httpclient;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.message.BasicNameValuePair;

/**
 * http client 使用
 * */
@Slf4j
public class HttpClientUse extends HttpClientService {



    public void getConfCall(String actionType,String id) {

        String url = "http://127.0.0.1:8081";

        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>();
        List<BasicNameValuePair> postBody = new ArrayList<BasicNameValuePair>();
        urlParams.add(new BasicNameValuePair("id", id));
        postBody.add(new BasicNameValuePair("ActionType", actionType));
        postBody.add(new BasicNameValuePair("DeliverySessionId",id));
        exeHttpReq(url, true, urlParams, postBody, new GetConfCall());

    }

    public void exeHttpReq(String baseUrl, boolean isPost,
                           List<BasicNameValuePair> urlParams,
                           List<BasicNameValuePair> postBody,
                           FutureCallback<HttpResponse> callback) {

        try {
            System.out.println("enter exeAsyncReq");
            exeAsyncReq(baseUrl, isPost, urlParams, postBody, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 被回调的对象，给异步的httpclient使用
     *
     * */
    class GetConfCall implements FutureCallback<HttpResponse> {

        /**
         * 请求完成后调用该函数
         */
        @Override
        public void completed(HttpResponse response) {
            log.info("Code={},response={}",response.getStatusLine().getStatusCode(),getHttpContent(response));
            HttpClientUtils.closeQuietly(response);

        }

        /**
         * 请求取消后调用该函数
         */
        @Override
        public void cancelled() {
          log.info("connect is cancelled");
        }

        /**
         * 请求失败后调用该函数
         */
        @Override
        public void failed(Exception e) {

        }

    }
}