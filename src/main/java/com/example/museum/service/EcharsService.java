package com.example.museum.service;

import com.example.museum.entity.Running;
import com.example.museum.mapper.DeviceMapper;
import com.example.museum.mapper.EcharsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName EcharsService
 * @Author xiaomingds
 * @Date 2021/5/22 12:53
 **/
@Service
public class EcharsService {
    @Autowired
    private EcharsMapper echarsMapper;
    public List<Running>GetLine(String maddr,String saddr){
        return echarsMapper.GetLine(maddr,saddr);
    }
}
