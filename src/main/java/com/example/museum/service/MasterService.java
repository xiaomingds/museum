package com.example.museum.service;

import com.example.museum.entity.Master;
import com.example.museum.entity.device.Door;
import com.example.museum.entity.device.Lamp;
import com.example.museum.entity.device.Relay;
import com.example.museum.mapper.MasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MasterService
 * @Author xiaomingds
 * @Date 2021/1/28 15:37
 **/
@Service
public class MasterService {
    @Autowired
    private MasterMapper masterMapper;
    public List<Master> allmaster(){
        return masterMapper.allmaster();
    }

}
