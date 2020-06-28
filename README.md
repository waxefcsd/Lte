# Lte
1.该程序实现sessionclient的功能，

2.springboot框架实现 version 0.0.1

3.根据request的请求，得到starttime和stoptime，将请求放入线程池中，通过AsyncClient实现异步请求。
  程序中使用了工厂模式，使用HttpClientFactory封装同步和异步两种请求方式，在HttpClientService调用AsyncClient进行异步请求。
4.# UML
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



单元测试覆盖率:
controller	100% (1/1)	50% (1/2)	13% (3/23)
httpclient	100% (6/6)	73% (17/23)	73% (90/123)
timerTask	100% (1/1)	100% (2/2)	80% (8/10)
LteApplication	100% (1/1)	0% (0/1)	33% (1/3)