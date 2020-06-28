package com.ericsson.lte.httpclient;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * http client 业务逻辑处理类
 * */
@Slf4j
public class HttpClientService {



    protected void exeAsyncReq(String baseUrl, boolean isPost,
                               List<BasicNameValuePair> urlParams,
                               List<BasicNameValuePair> postBody, FutureCallback callback)
            throws Exception {

        if (baseUrl == null) {
            log.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }

        HttpRequestBase httpMethod;
        CloseableHttpAsyncClient hc = null;

        try {
            hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                    .getAsyncHttpClient();

            hc.start();

            HttpClientContext localContext = HttpClientContext.create();
            BasicCookieStore cookieStore = new BasicCookieStore();

            if (isPost) {
                httpMethod = new HttpPost(baseUrl);

                if (null != postBody) {
                    log.info("exeAsyncReq post postBody={}", postBody);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                            postBody, "UTF-8");
                    ((HttpPost) httpMethod).setEntity(entity);
                }

                if (null != urlParams) {

                    String getUrl = EntityUtils
                            .toString(new UrlEncodedFormEntity(urlParams));

                    httpMethod.setURI(new URI(httpMethod.getURI().toString()
                            + "?" + getUrl));
                }

            } else {

                httpMethod = new HttpGet(baseUrl);

                if (null != urlParams) {

                    String getUrl = EntityUtils
                            .toString(new UrlEncodedFormEntity(urlParams));

                    httpMethod.setURI(new URI(httpMethod.getURI().toString()
                            + "?" + getUrl));
                }
            }


           Future result= hc.execute(httpMethod,  callback);
            log.info("sendTime={},sendURL={},callbak={}",new Date(),
                    httpMethod.getURI(),JSONObject.toJSONString(result)
                    );

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            System.gc();
        }

    }

    protected String getHttpContent(HttpResponse response) {

        HttpEntity entity = response.getEntity();
        String body = null;

        if (entity == null) {
            return null;
        }

        try {

            body = EntityUtils.toString(entity, "utf-8");

        } catch (ParseException e) {

            log.warn("the response's content inputstream is corrupt", e);
        } catch (IOException e) {

            log.warn("the response's content inputstream is corrupt", e);
        }
        return body;
    }
}