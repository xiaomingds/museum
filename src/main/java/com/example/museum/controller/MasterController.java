package com.example.museum.controller;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.service.MasterService;
import com.example.museum.socket.SocketService;
import com.example.museum.util.ApiResultHandler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String message = maddr+"CL";
        System.out.println("重置网关信息 "+ message);
       // socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "重置成功", "");
      //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
    @GetMapping("/switch")
    @ApiOperation(value = "网关开关")
    public ApiResult switchMaster(@RequestParam String maddr,@RequestParam boolean sw) {
        String message = null;
        if(sw){
            message = "AB" + maddr+"01" + "RCD";
        }
        else
            message = "AB" + maddr+"00" + "RCD";

        System.out.println("发送网关信息 "+ message);
        // socketService.PostMessage(message);
        return ApiResultHandler.buildApiResult(200, "重置成功", "");
        //  return ApiResultHandler.buildApiResult(501, "重置网关成功", "");
    }
}
