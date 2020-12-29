package com.imooc.mm.entity;

import lombok.Data;

@Data
public class Employee {
    private String sn;

    private String password;

    private String name;

    private String departmentSn;

    private String post;

    Department department;
}