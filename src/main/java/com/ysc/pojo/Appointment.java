package com.ysc.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private int appointmentId;
    private int hanFuId;
    private int cabinetId;
    private int userId;
    private String pickUpCode;
    private String deliveryCode;
}
