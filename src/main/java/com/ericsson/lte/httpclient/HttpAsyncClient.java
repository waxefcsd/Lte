package com.ericsson.lte.httpclient;



import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;




import org.apache.http.auth.MalformedChallengeException;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

/**
 * 异步的HTTP请求对象
 */
public class HttpAsyncClient {

    private static int socketTimeout = 5000;// 设置等待数据超时时间5秒钟

    private static int connectTimeout = 2000;// 连接超时

    private static int poolSize = 300;// 连接池最大连接数

    private static int maxPerRoute = 300;// 并发


    // 异步httpclient
    private CloseableHttpAsyncClient asyncHttpClient;


    public HttpAsyncClient() {
        try {
            this.asyncHttpClient = createAsyncClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CloseableHttpAsyncClient createAsyncClient()
            throws KeyManagementException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException,
            MalformedChallengeException, IOReactorException {



        // 设置协议http对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy> create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .build();

        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .build();
        // 设置连接池大小
        ConnectingIOReactor ioReactor;
        ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(
                ioReactor, null, sessionStrategyRegistry, null);

        if (poolSize > 0) {
            conMgr.setMaxTotal(poolSize);
        }

        //每个主机最大的并发量
        if (maxPerRoute > 0) {
            conMgr.setDefaultMaxPerRoute(maxPerRoute);
        } else {
            conMgr.setDefaultMaxPerRoute(10);
        }


            return HttpAsyncClients.custom().setConnectionManager(conMgr)
                    .setDefaultCookieStore(new BasicCookieStore()).build();

    }

    public CloseableHttpAsyncClient getAsyncHttpClient() {
        return asyncHttpClient;
    }


}

