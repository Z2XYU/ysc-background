package com.ysc.service;

import com.ysc.pojo.LeaseDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LeaseService {

    void rentHanFu(Integer userId, Integer hanFuId, Integer siteId);

    List<LeaseDetail> searchMyAllOrders(Integer uid);

    List<LeaseDetail> searchMyOutstandingOrders(Integer uid);

//    Integer returnHanFu(Integer hanFuId, Integer siteId);

    Map<String, Object> CompleteTheOrder(int userId, int hanFuId, Integer siteId);
}
