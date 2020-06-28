package com.ericsson.lte.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.ericsson.lte.timerTask.MyRunnable;

import lombok.extern.slf4j.Slf4j;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/nbi")
public class LteController {

    ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);

    @RequestMapping(value = "/deliverysession",method = RequestMethod.POST)
    public String Deliverysession(HttpServletRequest request){

        String response=null;
        try {
            InputStream is = null;
            is = request.getInputStream();
            String bodyInfo = IOUtils.toString(is, "utf-8");
            JSONObject reqJson = JSON.parseObject(bodyInfo);
            log.info("req={}", JSONObject.toJSONString(reqJson));
            JSONObject req = reqJson.getJSONObject("DeliverySession");
            String DeliverySessionId = req.getString("DeliverySessionId");

            long StartTime = Long.valueOf((req.getString("StartTime"))).longValue();
            long StopTime = Long.valueOf((req.getString("StopTime"))).longValue();

            MyRunnable myRunnableStart=new MyRunnable("Start",DeliverySessionId);
            pool.schedule(myRunnableStart,StartTime, TimeUnit.MILLISECONDS);
            MyRunnable myRunnableStop=new MyRunnable("Stop",DeliverySessionId);
            pool.schedule(myRunnableStop,StopTime, TimeUnit.MILLISECONDS);

        }catch (Exception e){
            e.printStackTrace();
        }
        response="200";
        return response;
    }

}
