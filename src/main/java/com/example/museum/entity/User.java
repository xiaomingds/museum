package com.example.museum.entity;


import lombok.Data;

import java.util.Date;

@Data
public class User {
    int userId;
    String userDate;
    String loginName;
    String userName;
    String userAddress;
    String userPassword;
    String role;
}
