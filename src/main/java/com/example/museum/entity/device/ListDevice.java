package com.example.museum.entity.device;

import com.example.museum.entity.Slave;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ListDevice
 * @Author xiaomingds
 * @Date 2021/4/22 19:58
 **/
@Data
public class ListDevice {

    String maddr;
    Boolean warning;
    List<Slave>slaveList;
    List<Door>doorList;
    List<Lamp>lampList;
    List<Relay>relayList;



}
