package com.example.museum.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName UuidUtil
 * @Author xiaomingds
 * @Date 2021/1/12 18:06
 **/
public class UuidUtil {
    public static String creatOrder(String prefix){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String parse = simpleDateFormat.format(new Date());
        String randomString=String.format("%06d",new Random().nextInt(9999));
        String orderNo=prefix+parse+randomString;
        return orderNo;
    }
}
