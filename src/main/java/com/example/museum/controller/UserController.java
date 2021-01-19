package com.example.museum.controller;
import java.io.File;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.Permissions.MenuTreeVO;
import com.example.museum.entity.User;
import com.example.museum.entity.Login;
import com.example.museum.service.UserService;
import com.example.museum.util.Jwt.TokenUtil;
import com.example.museum.util.UploadImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import com.example.museum.util.ApiResultHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import com.example.museum.util.Jwt.TokenUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import com.example.museum.util.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "接口路径以及名称")
public class UserController {
     private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping("/get")
    @ApiOperation(value="用户查询")
    public ApiResult getuser(@RequestParam int userId) {
        System.out.println(userId);
        User user = userService.get(userId);

        return ApiResultHandler.buildApiResult(200, "查找成功", user);
    }

    @GetMapping("/getmenu")
    @ApiOperation(value="菜单查询")
    public ApiResult getuser(@RequestParam String loginName) {
        List<MenuTreeVO>menu = userService.getMenu(loginName);
        return ApiResultHandler.buildApiResult(200, "查找成功", menu);
    }



    @PostMapping("/login")
    @ApiOperation(value="用户登录")
    public ApiResult userLogin(@RequestBody Login login) {

        User user = userService.Login(login.getUsername());
        ApiResult apiResult =null;

            if( user.getUserPassword().equals(login.getPassword())){
              String token = TokenUtil.sign(user);
              System.out.println("验证token姓名  "+TokenUtil.verityName(token));
                return ApiResultHandler.buildApiResult(200, "登录成功", token);

            }
            return ApiResultHandler.buildApiResult(401, "用户名或密码不正确", "");


    }
    @GetMapping("/getInfo")
    @ApiOperation(value = "得到登录用户的信息")
    public ApiResult info(@RequestParam("token") String token){
        ApiResult res = new ApiResult();

        // 验证token的合法和有效性
        String userName = TokenUtil.verityName(token);// success:zhangsan1
        if(userName != null) {
            User user = userService.Login(userName);
            res.setData(user);
            res.setMessage("查找成功");
            res.setCode(200);
        }else {
            // 否则：500
            res.setCode(500);
            res.setMessage("不存在此用户");
        }

        return res;
    }
    @PostMapping("/logout")
    @ApiOperation(value = "退出")
    public ApiResult logout(@RequestHeader("X-Token") String token){

        ApiResult res = new ApiResult();
        // 验证token的合法和有效性
        String userName = TokenUtil.verityName(token);// success:zhangsan1
        res.setMessage("退出成功");
        res.setData("");
        res.setCode(200);
        return res;

    }

    //创建user
    @PostMapping("/creatUser")
    @ApiOperation(value="创建用户")
    public ApiResult CreatUser(@RequestBody User user) {
        System.out.println(user.getUserPassword() + " " + user.getUserName()+" "+user.getUserAddress() );
        user.setUserDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                                        format(Calendar.getInstance().getTime())) ;
        User user1 = userService.Login(user.getLoginName());
        if(user1 != null){
            return ApiResultHandler.buildApiResult(400, "用户名重复，请换个名试试","");
        }
        int res = userService.CreatUser(user);
        if(res == 1)
        return ApiResultHandler.buildApiResult(200, "创建成功", res);

        return ApiResultHandler.buildApiResult(500, "创建失败", res);
    }
    //删除用户
    @GetMapping("/delUser")
    @ApiOperation(value="根据userID删除用户")
    public ApiResult DelUser(@RequestParam int userId) {
        System.out.println("删除的 ID" + userId);
         int res  = userService.DelUser(userId);
         if(res == 1)
        return ApiResultHandler.buildApiResult(200, "已删除", res);

            return ApiResultHandler.buildApiResult(200, "已经删除", res);
    }
    //更新用户
    @PostMapping("/updateUser")
    @ApiOperation(value="更新用户信息")
    public ApiResult updateUser(@RequestBody User user) {
        System.out.println(user.getUserId()+" "+user.getUserPassword() + " " + user.getUserName()+" "+user.getUserAddress() );
        int res = userService.UpdatelUser(user);
        return ApiResultHandler.buildApiResult(200, "更新用户数据成功", res);
    }
    //查询所有用户
    @GetMapping("/allUser")
    @ApiOperation(value="查找所有用户")
    public ApiResult allUser(@RequestParam(defaultValue = "1") int page,@RequestParam int size) {
        PageHelper.startPage(page,size);//size为每页显示的个数
        System.out.println("多少页"+ page);
        List<User>allUser = userService.allUser();
        PageInfo pageInfo = new PageInfo(allUser,5);//显示前后页数
       // System.out.println(allUser.get(0).getUserName());
        if(allUser == null)
            return ApiResultHandler.buildApiResult(200, "暂无用户数据", "");
        return ApiResultHandler.buildApiResult(200, "查找成功", pageInfo);
    }



    @GetMapping("/userList")
    @ApiOperation(value="无分页用户查找")
    public ApiResult  userList() {
        List<User>allUser = userService.allUser();
        return ApiResultHandler.buildApiResult(200, "查找成功", allUser);
    }

    @ApiImplicitParam(name = "file", value = "文件流", dataType = "__file", paramType = "form")
    @ResponseBody
    @PostMapping("/upload")
    public ApiResult upload(@RequestParam("imgFile") MultipartFile imgFile){

        ApiResult msg = new ApiResult();
        if (imgFile.isEmpty()) {
            // 设置错误状态码
            msg.setCode(500);
            msg.setMessage("上传失败，请选择文件");
            return msg;
        }
        // 拿到文件名
        String filename = imgFile.getOriginalFilename();
        // 存放上传图片的文件夹
        File fileDir = UploadImageUtils.getImgDirFile();
        // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
        System.out.println(fileDir.getAbsolutePath());

        try {
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);
            System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -》 “绝对路径”
            imgFile.transferTo(newFile);
            msg.setMessage("上传成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

}
