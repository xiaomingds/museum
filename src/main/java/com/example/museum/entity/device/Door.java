package com.example.museum.entity.device;

import lombok.Data;

/**
 * @ClassName Door
 * @Author xiaomingds
 * @Date 2021/4/22 19:52
 **/
@Data
public class Door {
    String did;
    String mid;
    Boolean sw;
    String door_address;
    Boolean warning;
    String type;

}
