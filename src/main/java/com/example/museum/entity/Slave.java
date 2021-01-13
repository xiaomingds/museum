package com.example.museum.entity;

import lombok.Data;

/**
 * @ClassName Slave
 * @Author xiaomingds
 * @Date 2021/1/12 19:37
 **/
@Data
public class Slave {
    String id;
    String saddr;
    String maddr;
    String status;
    float temperature;
    float humidity;
    String batterycapacity;
    String Sleep;
    String errorcode;
    String dtime;
}
