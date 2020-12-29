package com.imooc.mm.service;

import com.imooc.mm.entity.Employee;

public interface GlobalService {
    Employee login(String sn,String password);
    void changePassword(Employee employee);
}
