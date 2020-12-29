package com.imooc.mm.service.impl;

import com.imooc.mm.service.EmployeeService;
import com.imooc.mm.dao.EmployeeMapper;
import com.imooc.mm.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void add(Employee employee) {
        employee.setPassword("000000");
        employeeMapper.insert(employee);
    }

    @Override
    public void edit(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void remove(String sn) {
        employeeMapper.delete(sn);
    }

    @Override
    public Employee get(String sn) {
        return employeeMapper.select(sn);
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectAll();
    }
}
