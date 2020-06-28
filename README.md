# Lte
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
└─target
    ├─classes
    │  │  12.puml
    │  │  application.yml
    │  │  logback.xml
    │  │  Lte.puml
    │  │  
    │  └─com
    │      └─ericsson
    │          └─lte
    │              │  LteApplication.class
    │              │  
    │              ├─controller
    │              │      LteController.class
    │              │      
    │              ├─httpclient
    │              │      HttpAsyncClient.class
    │              │      HttpClientFactory.class
    │              │      HttpClientService.class
    │              │      HttpClientUse$GetConfCall.class
    │              │      HttpClientUse.class
    │              │      HttpSyncClient.class
    │              │      
    │              └─timerTask
    │                      MyRunnable.class
    │                      
    ├─generated-sources
    │  └─annotations
    ├─generated-test-sources
    │  └─test-annotations
    ├─maven-status
    │  └─maven-compiler-plugin
    │      ├─compile
    │      │  └─default-compile
    │      │          createdFiles.lst
    │      │          inputFiles.lst
    │      │          
    │      └─testCompile
    │          └─default-testCompile
    │                  createdFiles.lst
    │                  inputFiles.lst
    │                  
    ├─surefire-reports
    │      com.ericsson.lte.LteApplicationTests.txt
    │      TEST-com.ericsson.lte.LteApplicationTests.xml
    │      
    └─test-classes
        └─com
            └─ericsson
                └─lte
                        LteApplicationTests.class
                        
# UML
![Image text](https://github.com/waxefcsd/Lte/blob/master/Lte.png)
该程序实现sessionclient的功能，根据request的请求，得到starttime和stoptime，将请求放入线程池中，通过AsyncClient实现异步请求。
程序中使用了工厂模式，使用HttpClientFactory封装同步和异步两种请求方式，在HttpClientService调用AsyncClient进行异步请求。
