package com.ysc.service.impl;


import com.ysc.mapper.HomeMapper;
import com.ysc.mapper.LeaseMapper;

import com.ysc.pojo.Appointment;
import com.ysc.pojo.HanFu;
import com.ysc.pojo.LeaseDetail;
import com.ysc.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class LeaseServiceImpl implements LeaseService {
    @Autowired
    private LeaseMapper leaseMapper;
    @Autowired
    private HomeMapper homeMapper;

    @Override
    public void rentHanFu(Integer userId, Integer hanFuId, Integer siteId) {
        System.out.println(hanFuId +" "+ userId+" "+siteId);
        leaseMapper.modifyHanFuState(hanFuId);
        leaseMapper.modifyCabinetSpace(hanFuId,siteId);
        LocalDateTime creatTime=LocalDateTime.now();
        leaseMapper.addLeaseOrder(userId,hanFuId,creatTime);
    }

    @Override
    public List<LeaseDetail> searchMyAllOrders(Integer uid){
        return leaseMapper.selectMyAllOrders(uid);
    }

    @Override
    public List<LeaseDetail> searchMyOutstandingOrders(Integer uid) {
        return leaseMapper.selectMyOutstandingOrders(uid);
    }


    @Override
    public Map<String, Object> CompleteTheOrder(int userId, int hanFuId, Integer siteId) {
        System.out.println(userId+" "+hanFuId+" "+siteId);
        LocalDateTime returnTime=LocalDateTime.now();
        LeaseDetail leaseDetail= leaseMapper.selectTheOrder(userId,hanFuId);
        HanFu hanFu= homeMapper.selectHanFuInfoById(hanFuId);
        Integer location=leaseMapper.selectVacantLocation(siteId).get(0);
        leaseMapper.returnHanFu(hanFuId,siteId,location);
        leaseMapper.updateCabinetSpace(hanFuId,siteId,location);
        leaseMapper.completeOrder(userId,hanFuId,returnTime);


        LocalDateTime startTime=leaseDetail.getStartTime();
        double rent=hanFu.getRent();
        Duration duration = Duration.between(startTime, returnTime);
        long days = duration.toDays();
        // 如果租赁时间不到一天，按1天来算
        if (days < 1) {
            days = 1;
        }
        Double totalCost=rent * days;

        Map<String, Object> result = new HashMap<>();
        result.put("totalCost", totalCost);
        result.put("location", location);
        return result;
    }

    @Override
    public Appointment searchDeliveryCode(Integer siteId, String deliveryCode) {
        return leaseMapper.selectDeliverCode(siteId,deliveryCode);
    }

    @Override
    public Appointment searchPickCode(Integer siteId, String pickUpCode) {
        return leaseMapper.selectPickCode(siteId,pickUpCode);
    }

    @Override
    public void createAppointment(Integer siteId, Integer hanFuId) {
        Random random = new Random();
        // 生成两个6位随机数字字符串（范围：000000 ~ 999999）
        String pickCode = String.format("%06d", random.nextInt(1000000));
        String deliveryCode = String.format("%06d", random.nextInt(1000000));

        leaseMapper.insertAppointment(siteId,hanFuId,pickCode,deliveryCode);
    }
}
