package com.example.museum.service;

import com.example.museum.entity.Gateway;
import com.example.museum.entity.User;
import com.example.museum.mapper.DeviceMapper;
import com.example.museum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;


    public List<Gateway> allgateway(){
        return deviceMapper.allgateway();
    }
}
