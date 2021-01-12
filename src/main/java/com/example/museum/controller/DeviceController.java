package com.example.museum.controller;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.DeviceError;
import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.service.DeviceService;
import com.example.museum.util.ApiResultHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.util.List;

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
    @ApiOperation(value="所有网关")
    public ApiResult gatewaysList(@RequestParam(defaultValue = "1") int page, @RequestParam int size) {
        PageHelper.startPage(page,size);//size为每页显示的个数

        List<Master>allmaster = deviceService.allmaster();
        PageInfo pageInfo = new PageInfo(allmaster,5);//显示前后页数

        return ApiResultHandler.buildApiResult(200, "查找成功", pageInfo);
    }
    @GetMapping("/errorList")
    @ApiOperation(value="故障字典")
    public ApiResult errorList() {
        List<DeviceError>deviceErrors  = deviceService.errorlist();
        return ApiResultHandler.buildApiResult(200, "查找成功", deviceErrors);
    }

    @GetMapping("/slaveList")
    @ApiOperation(value="网关设备")
    public ApiResult slaveList( @RequestParam String maddr) {
        List<Slave>slaveList  = deviceService.allslave(maddr);
        if (slaveList != null)
        return ApiResultHandler.buildApiResult(200, "查找成功", slaveList);
        return ApiResultHandler.buildApiResult(400, "此网关下暂无设备", "");
    }
}
