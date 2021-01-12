package com.example.museum.controller;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.Gateway;
import com.example.museum.entity.User;
import com.example.museum.service.DeviceService;
import com.example.museum.util.ApiResultHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/gatewaysList")
    @ApiOperation(value="所有网关设备")
    public ApiResult gatewaysList(@RequestParam(defaultValue = "1") int page, @RequestParam int size) {
        PageHelper.startPage(page,size);//size为每页显示的个数

        List<Gateway>allgateway  = deviceService.allgateway();
        PageInfo pageInfo = new PageInfo(allgateway,5);//显示前后页数

        return ApiResultHandler.buildApiResult(200, "查找成功", pageInfo);
    }
}
