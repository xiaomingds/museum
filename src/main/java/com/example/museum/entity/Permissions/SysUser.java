package com.example.museum.entity.Permissions;

import lombok.Data;

@Data
public class SysUser {
    Integer userId;
    String loginName;
    String password;
    String userName;

}
