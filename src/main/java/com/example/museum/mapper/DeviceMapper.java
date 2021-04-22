package com.example.museum.mapper;


import com.example.museum.entity.DeviceError;
import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.entity.User;
import com.example.museum.entity.device.Door;
import com.example.museum.entity.device.Lamp;
import com.example.museum.entity.device.Relay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DeviceMapper {

    List<Slave> allslave();
    List<Slave>  mslave(String maddr);
    List<DeviceError> errorList();
    int UpdateSlaver(Slave slave);
    Integer countUser();
    Integer countSlave();
    List<Door>  Doors(String mid);
    List<Lamp>  Lamps(String mid);
    List<Relay>  relays(String mid);

}
