package com.example.museum.mapper;

import com.example.museum.entity.Running;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName EcharsMapper
 * @Author xiaomingds
 * @Date 2021/5/22 12:50
 **/
@Mapper
@Component
public interface EcharsMapper {
    List<Running>GetLine(String maddr,String saddr);
}
