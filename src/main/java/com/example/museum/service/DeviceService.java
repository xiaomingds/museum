package com.example.museum.service;

import com.example.museum.entity.DeviceError;
import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.mapper.DeviceMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    public List<Integer> CountList(){
        List<Integer>dataList = new ArrayList<Integer>();
        dataList.add(deviceMapper.countUser());
        dataList.add(deviceMapper.countMaster());
        dataList.add(deviceMapper.countSlave());
        dataList.add(WarningSlave().size());
        return dataList;
    }
    public List<Master> allmaster(){
        return deviceMapper.allmaster();
    }

    public List<Slave> allslave(){
        return deviceMapper.allslave();
    }

    public  List<DeviceError>errorlist(){
        return deviceMapper.errorList();
    }
    public List<Slave>  mslave(String maddr){
        return  deviceMapper.mslave(maddr);
    }
    public int UpdateSlave(Slave slave){
        return deviceMapper.UpdateSlaver(slave);
    }
    public List<Slave> WarningSlave(){
        List<Slave> allSlave = deviceMapper.allslave();
        List<Slave> warningSlave = new ArrayList<Slave>();
       for (Slave s : allSlave) {
            if(s.getTemperature() > s.getHumidity_max()||s.getTemperature() < s.getTemperature_min()
                    || s.getHumidity() > s.getHumidity_max() || s.getHumidity() < s.getHumidity_min()
                    || s.getBatterycapacity() < s.getBatterycapacity_min()){
                warningSlave.add(s);
            }
        }
        return warningSlave;
    }
}
