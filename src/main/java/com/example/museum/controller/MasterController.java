package com.example.museum.controller;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.entity.device.Door;
import com.example.museum.entity.device.Lamp;
import com.example.museum.entity.device.ListDevice;
import com.example.museum.entity.device.Relay;
import com.example.museum.service.DeviceService;
import com.example.museum.service.MasterService;
import com.example.museum.socket.SocketService;
import com.example.museum.util.ApiResultHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

;

/**
 * @ClassName MasterController
 * @Author xiaomingds
 * @Date 2021/1/28 15:07
 **/
@CrossOrigin
@RestController
@RequestMapping("/master")
@Api(tags = "网关管理")
public class MasterController {
    @Autowired
    SocketService socketService;
    @Autowired
    MasterService masterService;
    @Autowired
    DeviceService deviceService;

    @GetMapping("/masterList")
    @ApiOperation(value = "分页查找所有网关master")
    public ApiResult gatewaysList(@RequestParam(defaultValue = "1") int page, @RequestParam int size) {
        PageHelper.startPage(page, size);//size为每页显示的个数

        List<Master> allmaster = masterService.allmaster();
        PageInfo pageInfo = new PageInfo(allmaster, 5);//显示前后页数

        return ApiResultHandler.buildApiResult(200, "查找成功", pageInfo);
    }


    @GetMapping("/master")
    @ApiOperation(value = "查找所有网关不分页")
    public ApiResult master() {
        List<Master> allmaster = masterService.allmaster();

        return ApiResultHandler.buildApiResult(200, "查找成功", allmaster);
    }

    @GetMapping("/reset")
    @ApiOperation(value = "重置网关")
    public ApiResult restMaster(@RequestParam String maddr) {
        //10.23.129.231
        String message = "-------------------\nHTTP1\nMaster:" + maddr + '\n' + "SEND:CL\n-------------------\n";
        System.out.println("重置网关信息 \n" + message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "重置成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }

    @GetMapping("/switch")
    @ApiOperation(value = "网关开关")
    public ApiResult switchMaster(@RequestParam String maddr, @RequestParam boolean sw) {
        String message = null;
        if (sw) {
            message = "-------------------\nHTTP6\nMaster:" + maddr + '\n' + "SEND:" + "AB" + maddr + "01RCD\n-------------------\n";
        } else
            message = "-------------------\nHTTP6\nMaster:" + maddr + '\n' + "SEND:" + "AB" + maddr + "00RCD\n-------------------\n";

        System.out.println("发送网关信息 " + message);
        socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "重置成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }

    @GetMapping("/retime")
    @ApiOperation(value = "调节时间")
    public ApiResult MasterTime(@RequestParam String maddr) {
        //10.23.129.231
        String time;
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        time = formatter.format(date);
        String nowTime = time.substring(2);
        String message = "-------------------\nHTTP2\nMaster:" + maddr + '\n' + "SEND:ABAA" + nowTime + "CD\n-------------------\n";
        System.out.println("调节网关时间 \n" + message);

        // socketService.PostMessage(message);

        return ApiResultHandler.buildApiResult(200, "调节时间", "");
    }

    @GetMapping("ldevice")
    @ApiOperation("显示all网关all设备")
    public ApiResult MasterTime() {
        List<ListDevice>listDevices  = new ArrayList<ListDevice>();
        List<Master> allmaster = masterService.allmaster();
        for(Master ma : allmaster){
            ListDevice deviceList = new ListDevice();

            List<Slave> slaves= deviceService.mslave(ma.getMaddr());
            List<Door>doors = deviceService.Doors(ma.getMid());
            List<Lamp>lamps = deviceService.Lamps(ma.getMid());
            List<Relay>relays = deviceService.relays(ma.getMid());

            deviceList.setMaddr(ma.getMaddr());
            deviceList.setSlaveList(slaves);
            deviceList.setDoorList(doors);
            deviceList.setLampList(lamps);
            deviceList.setRelayList(relays);

            listDevices.add(deviceList);

        }
        return ApiResultHandler.buildApiResult(200, "查找成功", listDevices);
    }
}
