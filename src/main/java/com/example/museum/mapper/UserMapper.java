package com.example.museum.mapper;


import com.example.museum.entity.Permissions.MenuItem;
import com.example.museum.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface  UserMapper {
    User get(int userid);
    User Login(String loginName);
    int CreatUser(User user);
    int DelUser(int userId);
    int UpdatelUser(User user);
    List<User> allUser();

    //shiro
    List<MenuItem> getMenu(String loginName);
}
