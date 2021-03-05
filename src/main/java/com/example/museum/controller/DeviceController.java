package com.example.museum.controller;

import com.example.museum.entity.*;
import com.example.museum.service.DeviceService;
import com.example.museum.socket.SocketService;
import com.example.museum.util.ApiResultHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName DeviceController
 * @Author xiaomingds
 * @Date 2021/1/12 12:40
 **/
@CrossOrigin
@RestController
@RequestMapping("/device")
@Api(tags = "设备管理")
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    SocketService socketService;


    @GetMapping("/errorList")
    @ApiOperation(value = "故障字典")
    public ApiResult errorList() {
        List<DeviceError> deviceErrors = deviceService.errorlist();
        return ApiResultHandler.buildApiResult(200, "查找成功", deviceErrors);
    }

    @GetMapping("/slaveList")
    @ApiOperation(value = "查找所有网关下所有设备")
    public ApiResult slaveList() {
        List<Slave> slaveList = deviceService.allslave();
        if (slaveList != null)
            return ApiResultHandler.buildApiResult(200, "查找成功", slaveList);
        return ApiResultHandler.buildApiResult(400, "无网关设备", "");
    }

    @GetMapping("/mslaveList")
    @ApiOperation(value = "查找对应网关下的设备")
    public ApiResult mslaveList(@RequestParam String maddr) {
        System.out.println("后台收到的网关地址 " + maddr);
        List<Slave> slaveList = deviceService.mslave(maddr);
        if (slaveList != null)
            return ApiResultHandler.buildApiResult(200, "查找成功", slaveList);
        return ApiResultHandler.buildApiResult(400, "此网关下暂无设备", "");
    }

    @PostMapping("/editDevice")
    @ApiOperation(value = "更新设备报警信息")
    public ApiResult editDevice(@RequestBody Slave slave) {
        int res = deviceService.UpdateSlave(slave);

        String message = slave.getMaddr() + slave.getSaddr() + slave.getStatus() + slave.getTemperature()
                + slave.getHumidity() + slave.getBatterycapacity_min() + slave.getSleep()
                + new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").
                format(Calendar.getInstance().getTime());

        return ApiResultHandler.buildApiResult(501, "硬件设备连接不上", res);
    }

    @GetMapping("/warningslave")
    @ApiOperation(value = "报警设备")
    public ApiResult warningSlave() {
        List<Slave> slaveList = deviceService.WarningSlave();
        if (slaveList != null)
            return ApiResultHandler.buildApiResult(200, "查找成功", slaveList);
        return ApiResultHandler.buildApiResult(200, "暂无报警设备", "");
    }

    @GetMapping("/indexList")
    @ApiOperation(value = "首页数量统计")
    public ApiResult indexList() {
        List<Integer> dataList = deviceService.CountList();
        return ApiResultHandler.buildApiResult(200, "查找成功", dataList);
    }


    @PostMapping("/massage")
    @ApiOperation(value = "向设备发送信息")
    public ApiResult sendMassage(@RequestParam String massage) {
       socketService.PostMessage(massage);
        return ApiResultHandler.buildApiResult(501, "硬件设备连接不上", "");
    }

    @GetMapping("/switch")
    @ApiOperation(value = "门开关")
    public ApiResult doorDevice(@RequestParam String maddr,@RequestParam String saddr,@RequestParam boolean sw) {
        String message = null;
        if(sw){
            message = "-------------------\nHTTP4\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+"01UCD\n-------------------";
        }
        else
            message = "-------------------\nHTTP4\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+"01DCD\n-------------------";
        System.out.println("发送网关信息 "+ message);
        // socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "门开关成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/light")
    @ApiOperation(value = "灯亮度")
    public ApiResult lightDevice(@RequestParam String maddr,@RequestParam String saddr,@RequestParam int lamp) {
       String message = "-------------------\nHTTP5\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+ lamp + "ECD\n-------------------";

        System.out.println("发送网关信息 "+ message);
        // socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "灯亮度调节成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/sleep")
    @ApiOperation(value = "设备休眠时间")
    public ApiResult sleepDevice(@RequestParam String  maddr,@RequestParam String saddr,@RequestParam String sleep) {
        String message = "-------------------\nHTTP5\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+ sleep + "SCD\n-------------------";

        System.out.println("发送网关信息 "+ message);
        // socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "调节休眠时间成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }

}
