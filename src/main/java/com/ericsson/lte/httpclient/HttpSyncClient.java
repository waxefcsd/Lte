package com.ericsson.lte.httpclient;


import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;


/**
 * 同步的HTTP请求对象，支持post与get方法以及可设置代理
 */
@Slf4j
public class HttpSyncClient {


    private static int socketTimeout = 1000;// 设置等待数据超时时间5秒钟 根据业务调整

    private static int connectTimeout = 20000;// 连接超时

    private static int maxConnNum = 4000;// 连接池最大连接数

    private static int maxPerRoute = 1500;// 每个主机的并发最多只有1500

    private static PoolingClientConnectionManager cm;

    private static HttpParams httpParams;

    private static final String DEFAULT_ENCODING = Charset.defaultCharset()
            .name();


    private CloseableHttpClient httpClient;

    /* 应用启动的时候就应该执行的方法

     */
    public HttpSyncClient() {

        this.httpClient = createClient();
    }

    public CloseableHttpClient createClient() {
        /* 初始化连接池

         */
        cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(maxConnNum);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        httpParams = new BasicHttpParams();
        httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
                HttpVersion.HTTP_1_1);
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                connectTimeout);
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
                socketTimeout);

        CloseableHttpClient httpclient = new DefaultHttpClient(cm, httpParams);

        return httpclient;

    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }


}