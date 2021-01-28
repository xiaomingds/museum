package com.example.museum.service;


import com.example.museum.entity.Permissions.MenuItem;
import com.example.museum.entity.Permissions.MenuTreeVO;
import com.example.museum.entity.User;
import com.example.museum.mapper.UserMapper;
import com.example.museum.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User get(int userid) {
        return userMapper.get(userid);
    }
    public User Login(String loginName){
        return userMapper.Login(loginName);
    }
    public  int CreatUser(User user){
       return userMapper.CreatUser(user);
    }
    public  int DelUser(int userId){
        return userMapper.DelUser(userId);
    }
    public int UpdatelUser(User user){
        return userMapper.UpdatelUser(user);
    }
    public List<User> allUser(){
        return userMapper.allUser();
    }

    public  List<MenuTreeVO> getMenu(String loginName){
       List<MenuItem>menus  = userMapper.getMenu(loginName);
        List<MenuTreeVO> menuTreeVOS = new ArrayList<>();
        TreeUtil treeUtil= new TreeUtil();
        for (MenuItem menu : menus) {
            MenuTreeVO menuTreeVO = new MenuTreeVO();
            BeanUtils.copyProperties(menu, menuTreeVO);
            menuTreeVOS.add(menuTreeVO);
        }
        return  TreeUtil.toTree(menuTreeVOS);
    }
    public User FindUSer(String userName){
        return userMapper.FindUser(userName);
    }
}
