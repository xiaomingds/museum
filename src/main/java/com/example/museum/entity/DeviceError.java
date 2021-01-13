package com.example.museum.entity;

import lombok.Data;

/**
 * @ClassName DeviceError
 * @Author xiaomingds
 * @Date 2021/1/12 17:17
 **/
@Data
public class DeviceError {
    Integer id;
    String type;
    String TypeName;
    String Code;
    String Describe;
}
