package com.imooc.mm.entity;

import lombok.Data;

@Data
public class ClaimVoucherItem {
    private Integer id;
    private Integer claimVoucherId;
    private String item;
    private Double amount;
    private String comment;
}