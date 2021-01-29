package com.example.museum.mapper;

import com.example.museum.entity.Master;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MasterMapper {
    List<Master> allmaster();
    Integer countMaster();


}
