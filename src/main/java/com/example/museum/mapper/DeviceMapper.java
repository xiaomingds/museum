package com.example.museum.mapper;


import com.example.museum.entity.Gateway;
import com.example.museum.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeviceMapper {
    List<Gateway> allgateway();
}
