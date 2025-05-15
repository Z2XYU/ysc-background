package com.ysc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseDetail {
    private Integer id;
    private Integer userId;
    private Integer hanfuId;
    private LocalDateTime startTime;
    private LocalDateTime returnTime;
    private Boolean leaseStatue;
}
