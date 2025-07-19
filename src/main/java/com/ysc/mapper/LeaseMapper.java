package com.ysc.mapper;

import com.ysc.pojo.Appointment;
import com.ysc.pojo.LeaseDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LeaseMapper {



    @Update("UPDATE hanfu SET lease_statue = 1, cabinet_location = 0 WHERE hanfu_id=#{hanFuId}")
    void modifyHanFuState(Integer hanFuId);


    @Insert("INSERT INTO Lease (user_id,hanfu_id,start_time) VALUES ( #{userId},#{hanFuId},#{creatTime})")
    void addLeaseOrder(int userId, int hanFuId, LocalDateTime creatTime);


    @Select("select * from Lease where user_id=#{uid}")
    List<LeaseDetail> selectMyAllOrders(Integer uid);

    @Select("select * from Lease where user_id=#{uid} and lease_statue=false")
    List<LeaseDetail> selectMyOutstandingOrders(Integer uid);

    @Select("select * from Lease where user_id=#{userId} and hanfu_id=#{hanFuId} and lease_statue=false")
    LeaseDetail selectTheOrder(int userId, int hanFuId);

    @Update("update hanfu set lease_statue=0 ,cabinet_id=#{siteId},cabinet_location=#{location} where hanfu_id=#{hanFuId}")
    void returnHanFu(int hanFuId, Integer siteId, Integer location);
    @Update("update Lease set lease_statue=1 ,return_time=#{returnTime} where hanfu_id=#{hanFuId} and user_id=#{userId}")
    void completeOrder(int userId, int hanFuId, LocalDateTime returnTime);

    @Select("select location from cabinet_space where hanfu_id is null and  cabinet_id=#{siteId}")
    List<Integer> selectVacantLocation(Integer siteId);

    @Update("update cabinet_space set hanfu_id=null  where hanfu_id=#{hanFuId} and cabinet_id=#{siteId}")
    void modifyCabinetSpace(Integer hanFuId, Integer siteId);

    @Update("update cabinet_space set hanfu_id=#{hanFuId} where cabinet_id=#{siteId} and location=#{location}")
    void updateCabinetSpace(int hanFuId, Integer siteId, Integer location);

    @Select("SELECT * FROM appointment WHERE cabinet_id = #{siteId} AND delivery_code = #{deliveryCode}")
    Appointment selectDeliverCode(Integer siteId, String deliveryCode);
    @Select("SELECT * FROM appointment WHERE cabinet_id = #{siteId} AND pickup_code = #{pickUpCode}")
    Appointment selectPickCode(Integer siteId, String pickUpCode);

    @Insert("INSERT INTO appointment (cabinet_id, hanfu_id, pickup_code, delivery_code) " +
            "VALUES (#{siteId}, #{hanFuId}, #{pickCode}, #{deliveryCode})")
    void insertAppointment(Integer siteId, Integer hanFuId, String pickCode, String deliveryCode);
}
