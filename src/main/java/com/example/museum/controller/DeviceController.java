package com.example.museum.controller;

import com.example.museum.entity.*;
import com.example.museum.entity.device.Door;
import com.example.museum.entity.device.Lamp;
import com.example.museum.entity.device.Relay;
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
    public ApiResult doorDevice(@RequestParam String maddr,@RequestParam String door_address,@RequestParam boolean sw,
    @RequestParam String type) {

        String message = null;
        if(sw){
            if(type.equals("1")){
                message = "-------------------\nHTTP4\nMaster:"+maddr+'\n'+"SEND:"+"AB"+door_address+"01UCD\n-------------------\n";

            }
            else{
                message = "-------------------\nHTTP4\nMaster:"+maddr+'\n'+"SEND:"+"AB"+door_address+"02UCD\n-------------------\n";

            }

        }
        else {
            if (type.equals("1")) {
                message = "-------------------\nHTTP4\nMaster:" + maddr + '\n' + "SEND:" + "AB" + door_address + "01DCD\n-------------------\n";

            } else
                message = "-------------------\nHTTP4\nMaster:" + maddr + '\n' + "SEND:" + "AB" + door_address + "02DCD\n-------------------\n";
        }
        System.out.println("发送网关信息 "+ message);
         socketService.PostMessage(message);

        return ApiResultHandler.buildApiResult(200, "门开关成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }

    @GetMapping("/light")
    @ApiOperation(value = "灯亮度")
    public ApiResult lightDevice(@RequestParam String maddr,@RequestParam String lamp_address,@RequestParam int lamp) {
        String ll="";
        if(lamp >= 0 && lamp <=9){
            ll="0";
            ll +=lamp;
        }
        else if(lamp == 100)
            ll ="99";
        else
            ll += lamp;
        String message = "-------------------\nHTTP5\nMaster:"+maddr+'\n'+"SEND:"+"AB"+lamp_address+ ll + "ECD\n-------------------\n";

        System.out.println("发送网关信息 "+ message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "灯亮度调节成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }

    @GetMapping("/sleep")
    @ApiOperation(value = "设备休眠时间")
    public ApiResult sleepDevice(@RequestParam String  maddr,@RequestParam String saddr,@RequestParam String sleep) {
        String sleeptime = null;
        String message = null;
        int time = Integer.parseInt(sleep);
        if(sleep.length() ==1){
           sleeptime  = "0" + sleep;
            message = "-------------------\nHTTP3\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+ sleeptime + "SCD\n-------------------\n";

        }
        else if( time > 99){
            if(String.valueOf(time/60).length() == 1)
                sleeptime = "0" + time/60;
            else
                sleeptime = String.valueOf(time/60);
            message = "-------------------\nHTTP3\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+ sleeptime + "MCD\n-------------------\n";
        }
        else
            message = "-------------------\nHTTP3\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+ sleep + "SCD\n-------------------\n";


        System.out.println("发送网关信息 \n"+ message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "调节休眠时间成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }


    @GetMapping("/otherswitch")
    @ApiOperation(value = "继电器开关")
    public ApiResult switchMaster(@RequestParam String maddr, @RequestParam String relay_address, @RequestParam boolean sw) {
        String message = null;
        if (sw) {
            message = "-------------------\nHTTP6\nMaster:" + maddr + '\n' + "SEND:" + "AB" + relay_address + "01RCD\n-------------------\n";
        } else
            message = "-------------------\nHTTP6\nMaster:" + maddr + '\n' + "SEND:" + "AB" + relay_address + "00RCD\n-------------------\n";

        System.out.println("发送网关信息 " + message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "重置成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }

    @GetMapping("/findswitch")
    @ApiOperation(value = "具体某一个开关")
    public ApiResult findSwich(@RequestParam  String mid, @RequestParam String door_address){
        Door door = deviceService.findDoor(mid,door_address);
        return ApiResultHandler.buildApiResult(200, "查找成功", door);

    }

    @GetMapping("/findlamp")
    @ApiOperation(value = "具体某一个灯")
    public ApiResult findLamp(@RequestParam  String mid, @RequestParam String lamp_address){
        Lamp lamp = deviceService.findLamp(mid,lamp_address);
        return ApiResultHandler.buildApiResult(200, "查找成功", lamp);
    }
    @GetMapping("/findrelay")
    @ApiOperation(value = "具体某一个继电器")
    public ApiResult findRelay(@RequestParam  String mid, @RequestParam String relay_address){
        Relay relay  = deviceService.findRelay(mid,relay_address);
        return ApiResultHandler.buildApiResult(200, "查找成功", relay);
    }

    @GetMapping("/cleardoor")
    @ApiOperation(value = "解除门报警")
    public ApiResult clearDoor(@RequestParam String maddr,@RequestParam String door_address) {
        System.out.println("门的地址 "+ door_address);

        String message = null;
        message = "-------------------\nHTTP4\nMaster:"+maddr+'\n'+"SEND:"+"AB"+door_address+"00TCD\n-------------------\n";
        System.out.println("发送网关信息 "+ message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "解除门报警成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/clearmovie")
    @ApiOperation(value = "解除移动报警")
    public ApiResult clearMovie(@RequestParam String maddr) {

        String message = null;
        message = "-------------------\nHTTP4\nMaster:"+maddr+'\n'+"SEND:"+"AB"+"00TCD\n-------------------\n";
        System.out.println("发送网关信息 "+ message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "解除移动报警成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/temperaturewaring")
    @ApiOperation(value = "解除温度传感器报警")
    public ApiResult clearTemperatureWaring(@RequestParam String maddr,@RequestParam String saddr) {

        String message = null;
        message = "-------------------\nHTTP6\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+"00TCD\n-------------------\n";
        System.out.println("发送网关信息 "+ message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "解除温度传感器报警", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/moviewaring")
    @ApiOperation(value = "解除移动传感器报警")
    public ApiResult clearMovieWaring(@RequestParam String maddr,@RequestParam String saddr) {

        String message = null;
        message = "-------------------\nHTTP6\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+"00TCD\n-------------------\n";
        System.out.println("发送网关信息 "+ message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "解除移动传感器报警", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/UpdateWarningSlaver")
    @ApiOperation(value = "设备报警开关/更新数据库了")
    public ApiResult UpdateWarningSlaver(@RequestParam String maddr,@RequestParam   String saddr, @RequestParam Boolean switch_warning){

        String message = null;
        if(switch_warning){
            message = "-------------------\nHTTP6\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+"00ACD\n-------------------\n";
        }
        else{
            message = "-------------------\nHTTP6\nMaster:"+maddr+'\n'+"SEND:"+"AB"+saddr+"00TCD\n-------------------\n";
        }
        System.out.println("发送网关信息 "+ message);
        socketService.PostMessage(message);
         if(deviceService.UpdateWarningSlaver(maddr,saddr) == 1){
             return ApiResultHandler.buildApiResult(200, "", "");
         }
        return ApiResultHandler.buildApiResult(500, "", "");

    }

}
