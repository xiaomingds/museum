package com.example.museum.controller;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.DeviceError;
import com.example.museum.entity.Running;
import com.example.museum.mapper.EcharsMapper;
import com.example.museum.service.DeviceService;
import com.example.museum.socket.SocketService;
import com.example.museum.util.ApiResultHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EcharsController
 * @Author xiaomingds
 * @Date 2021/5/22 12:49
 **/
@CrossOrigin
@RestController
@RequestMapping("/echarsline")
@Api(tags = "图表")
public class EcharsController {
    @Autowired
    EcharsMapper echarsMapper;



    @GetMapping("/line")
    @ApiOperation(value = "温湿度曲线")
    public ApiResult lineList(@RequestParam(defaultValue = "01") String maddr, @RequestParam(defaultValue = "01") String saddr,
                              @RequestParam(required = false) String dateval) {
        String [] strArray = dateval.split(" ");


        String begindate =strArray[0]+strArray[1];
        String enddate = strArray[2]+strArray[3];

        List<Running> runnings = echarsMapper.GetLine(maddr,saddr,begindate,enddate);
        List<String>listT =new ArrayList<String>();
        List<String>listH = new ArrayList<String>();
        List<String>listTime = new ArrayList<String>();
        for(Running running:runnings){
            if(running.getTemperature()!=null && running.getHumidity()!=null) {
                listT.add(running.getTemperature());
                listH.add(running.getHumidity());
                listTime.add(running.getTime());
            }
        }
        Map<String,List<String>> mapLine = new HashMap<>();
        mapLine.put("T",listT);
        mapLine.put("H",listH);
        mapLine.put("time",listTime);

        return ApiResultHandler.buildApiResult(200, "查找成功", mapLine);
    }

}
