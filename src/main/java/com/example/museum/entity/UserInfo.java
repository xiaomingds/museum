package com.example.museum.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName UserInfo
 * @Author xiaomingds
 * @Date 2021/1/24 11:44
 **/
@Data
public class UserInfo {
    private List<String> roles;
    private String introduction;
    private String avatar;
    private String name;




}
