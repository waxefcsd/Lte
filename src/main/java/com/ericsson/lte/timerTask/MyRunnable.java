package com.ericsson.lte.timerTask;

import com.ericsson.lte.httpclient.HttpClientUse;
import lombok.SneakyThrows;

public class MyRunnable implements Runnable {
    private String actionType;
    private String deliverySessionId;

    public MyRunnable(String actionType,String deliverySessionId){
        this.actionType = actionType;
        this.deliverySessionId=deliverySessionId;
    }


    @Override
    public void run() {
        try {
            HttpClientUse httpClientUse=new HttpClientUse();
            httpClientUse.getConfCall(actionType,deliverySessionId);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
