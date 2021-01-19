package com.example.museum.controller;

import com.example.museum.entity.*;
import com.example.museum.service.DeviceService;
import com.example.museum.util.ApiResultHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/masterList")
    @ApiOperation(value="分页查找所有网关master")
    public ApiResult gatewaysList(@RequestParam(defaultValue = "1") int page, @RequestParam int size) {
        PageHelper.startPage(page,size);//size为每页显示的个数

        List<Master>allmaster = deviceService.allmaster();
        PageInfo pageInfo = new PageInfo(allmaster,5);//显示前后页数

        return ApiResultHandler.buildApiResult(200, "查找成功", pageInfo);
    }


    @GetMapping("/master")
    @ApiOperation(value="查找所有网关不分页")
    public ApiResult master() {
        List<Master>allmaster = deviceService.allmaster();

        return ApiResultHandler.buildApiResult(200, "查找成功", allmaster);
    }

    @GetMapping("/errorList")
    @ApiOperation(value="故障字典")
    public ApiResult errorList() {
        List<DeviceError>deviceErrors  = deviceService.errorlist();
        return ApiResultHandler.buildApiResult(200, "查找成功", deviceErrors);
    }

    @GetMapping("/slaveList")
    @ApiOperation(value="查找所有网关下所有设备")
    public ApiResult slaveList() {
        List<Slave>slaveList  = deviceService.allslave();
        if (slaveList != null)
        return ApiResultHandler.buildApiResult(200, "查找成功", slaveList);
        return ApiResultHandler.buildApiResult(400, "无网关设备", "");
    }
    @GetMapping("/mslaveList")
    @ApiOperation(value="查找对应网关下的设备")
    public ApiResult mslaveList(@RequestParam String maddr) {
        System.out.println("后台收到的网关地址 "+ maddr);
        List<Slave>slaveList  = deviceService.mslave(maddr);
        if (slaveList != null)
            return ApiResultHandler.buildApiResult(200, "查找成功", slaveList);
        return ApiResultHandler.buildApiResult(400, "此网关下暂无设备", "");
    }
    @PostMapping("/editDevice")
    @ApiOperation(value="更新设备报警信息")
    public ApiResult editDevice(@RequestBody Slave slave) {
        System.out.println(slave.getTemperature_max()+" "+slave.getHumidity_max() + " " + slave.getSleep());
        int res =deviceService.UpdateSlave(slave);
        if(res == 1)
        return ApiResultHandler.buildApiResult(200, "更新设备信息成功", res);
        return ApiResultHandler.buildApiResult(200, "更新设备信息失败", res);
    }
}
