package com.ericsson.lte;

import com.alibaba.fastjson.JSONObject;
import com.ericsson.lte.controller.LteController;
import com.ericsson.lte.httpclient.HttpClientFactory;
import com.ericsson.lte.httpclient.HttpClientService;
import com.ericsson.lte.httpclient.HttpClientUse;
import com.ericsson.lte.timerTask.MyRunnable;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LteApplication.class)
class LteApplicationTests {

      @Test
      void TestLte(){
          MyRunnable myRunnableStart=new MyRunnable("Start","1");
          MyRunnable myRunnableStop=new MyRunnable("Stop","1");
          myRunnableStart.run();
          myRunnableStop.run();
      }

    @Test
    void TestHttpAsyncClient(){
        String baseUrl="http://127.0.0.1:8081";
        boolean isPost=true;
        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>();
        List<BasicNameValuePair> postBody = new ArrayList<BasicNameValuePair>();
        urlParams.add(new BasicNameValuePair("id", "11"));
        postBody.add(new BasicNameValuePair("ActionType", "Stop"));
        postBody.add(new BasicNameValuePair("DeliverySessionId","11"));
        HttpRequestBase httpMethod;
        FutureCallback callback=null;
        CloseableHttpAsyncClient hc = null;
        for(int i=0;i<10;i++) {
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


                localContext.setAttribute(HttpClientContext.COOKIE_STORE,
                        cookieStore);

                Future result = hc.execute(httpMethod, localContext, callback);
                log.info("sendTime={},sendURL={},callbak={}", new Date(),
                        httpMethod.getURI(), JSONObject.toJSONString(result)
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    void TestHttpSyncClient() throws IOException {
        String baseUrl="http://127.0.0.1:8081";
        HttpUriRequest httpMethod;
        CloseableHttpClient hc =HttpClientFactory.getInstance().getHttpClientPool().getHttpClient();
        HttpPost httpPost = new HttpPost(baseUrl);
        HttpResponse response=hc.execute(httpPost);
        log.info("response={}",response);
    }


}
