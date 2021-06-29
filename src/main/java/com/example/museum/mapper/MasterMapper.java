package com.example.museum.mapper;

import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.entity.device.Door;
import com.example.museum.entity.device.Lamp;
import com.example.museum.entity.device.Relay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MasterMapper {
    List<Master> allmaster();
    Integer countMaster();
    Master findMaster(String maddr);
}
