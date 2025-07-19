package com.ysc.controller;

import com.ysc.pojo.Appointment;
import com.ysc.pojo.LeaseDetail;
import com.ysc.pojo.Result;
import com.ysc.service.LeaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lease")
@Slf4j
@CrossOrigin
public class LeaseController {
    @Autowired
    private LeaseService leaseService;
    @PostMapping("/order/{siteId}")
    public Result createLeaseOrders(@RequestBody LeaseDetail leaseDetail,@PathVariable Integer siteId){
//        System.out.println(leaseDetail);
        Integer hanFuId= leaseDetail.getHanfuId();
        Integer userId= leaseDetail.getUserId();
        System.out.println(hanFuId +" "+ userId+" "+siteId);
        leaseService.rentHanFu(userId,hanFuId,siteId);
        return Result.success("订单创建成功");
    };

    @GetMapping("/myAllOrders/{uid}")
    public Result showMyAllOrders(@PathVariable Integer uid){
        List<LeaseDetail> leaseDetail = leaseService.searchMyAllOrders(uid);
        return Result.success(leaseDetail);
    }

    @GetMapping("/myOutstandingOrders/{uid}")
    public Result showMyOutstandingOrders(@PathVariable Integer uid){
        List<LeaseDetail> leaseDetail= leaseService.searchMyOutstandingOrders(uid);
        return Result.success(leaseDetail);
    }

//    @PostMapping("/returnHanfu/{siteId}")
//    public Result returnHanFu(@RequestBody Integer hanFuId,@PathVariable Integer siteId){
//        Integer location=leaseService.returnHanFu(hanFuId,siteId);
//        return Result.success(location);
//    }

    @PostMapping("/CompleteOrder/{siteId}")
    public Result settleTheOrder(@RequestBody LeaseDetail leaseDetail,@PathVariable Integer siteId){
        int hanFuId= leaseDetail.getHanfuId();
        int userId= leaseDetail.getUserId();
        Map<String, Object> totalCostAndLocation=leaseService.CompleteTheOrder(userId,hanFuId,siteId);
        return Result.success(totalCostAndLocation);
    }

    @PostMapping("/appointmentOrder")
    public  Result addAppointmentOrder( @RequestParam Integer siteId,
                                        @RequestParam Integer hanFuId){
        leaseService.createAppointment(siteId,hanFuId);
        return  Result.success();
    }


    @GetMapping("/appointmentDeliveryOrders/{siteId}")
    public Result findDeliveryCode(@PathVariable Integer siteId,String deliveryCode){
        Appointment result = leaseService.searchDeliveryCode(siteId,deliveryCode);
        return Result.success(result);
    }

    @GetMapping("/appointmentPickUpOrders/{siteId}")
    public Result findPickCode(@PathVariable Integer siteId,String pickUpCode){
        Appointment result = leaseService.searchPickCode(siteId,pickUpCode);
        return Result.success(result);
    }
}
