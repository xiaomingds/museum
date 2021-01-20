package com.example.museum.service;

import com.example.museum.entity.DeviceError;
import com.example.museum.entity.Master;
import com.example.museum.entity.Slave;
import com.example.museum.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;


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
