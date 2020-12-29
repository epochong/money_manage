package com.imooc.mm.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class ClaimVoucher {
    private Integer id;
    private String cause;
    private String createSn;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date createTime;
    private String nextDealSn;
    private Double totalAmount;
    private String status;
    private Employee creater;
    private Employee dealer;
}