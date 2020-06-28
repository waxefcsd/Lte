# Lte

# UML
![Image text](https://github.com/waxefcsd/Lte/blob/master/Lte.png)

# 目录

├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─ericsson
│  │  │          └─lte
│  │  │              │  LteApplication.java
│  │  │              │  
│  │  │              ├─controller
│  │  │              │      LteController.java
│  │  │              │      
│  │  │              ├─httpclient
│  │  │              │      HttpAsyncClient.java
│  │  │              │      HttpClientFactory.java
│  │  │              │      HttpClientService.java
│  │  │              │      HttpClientUse.java
│  │  │              │      HttpSyncClient.java
│  │  │              │      
│  │  │              └─timerTask
│  │  │                      MyRunnable.java
│  │  │                      
│  │  └─resources
│  │          application.yml
│  │          logback.xml
│  │          
│  └─test
│      └─java
│          └─com
│              └─ericsson
│                  └─lte
│                          LteApplicationTests.java
│                          


该程序实现sessionclient的功能，根据request的请求，得到starttime和stoptime，将请求放入线程池中，通过AsyncClient实现异步请求。
程序中使用了工厂模式，使用HttpClientFactory封装同步和异步两种请求方式，在HttpClientService调用AsyncClient进行异步请求。
