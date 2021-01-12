package com.example.museum.controller;

import com.example.museum.entity.ApiResult;
import com.example.museum.entity.Login;
import com.example.museum.entity.User;
import com.example.museum.service.UserService;
import com.example.museum.util.ApiResultHandler;
import com.example.museum.util.Jwt.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "登录")
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation(value="用户登录")
    public ApiResult userLogin(@RequestBody Login login) {

        User user = userService.Login(login.getUsername());
        System.out.println(user.getUserPassword()+" "+ login.getPassword());


        if(user.getUserPassword().equals(login.getPassword())){
            System.out.println(user.getUserPassword() + " " + user.getUserName());
            String token = TokenUtil.sign(user);
            return ApiResultHandler.buildApiResult(200, "登陆成功", token);
        }
        else
            return ApiResultHandler.buildApiResult(500, "用户名或密码错误", "");
    }
    @GetMapping("/userList")
    @ApiOperation(value="无分页用户查找")
    public ApiResult  userList() {
        List<User> allUser = userService.allUser();
        return ApiResultHandler.buildApiResult(200, "查找成功", allUser);
    }
}
