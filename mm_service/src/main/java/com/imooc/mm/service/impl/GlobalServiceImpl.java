package com.imooc.mm.service.impl;

import com.imooc.mm.service.GlobalService;
import com.imooc.mm.dao.EmployeeMapper;
import com.imooc.mm.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("globalService")
public class GlobalServiceImpl implements GlobalService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(String sn, String password) {
        Employee employee = employeeMapper.select(sn);
        if(employee!=null&&employee.getPassword().equals(password)){
            return  employee;
        }
        return null;
    }

    @Override
    public void changePassword(Employee employee) {
        employeeMapper.update(employee);
    }
}
