package com.imooc.mm.service.impl;

import com.imooc.mm.service.DepartmentService;
import com.imooc.mm.dao.DepartmentMapper;
import com.imooc.mm.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public void add(Department department) {
        departmentMapper.insert(department);
    }

    public void edit(Department department) {
        departmentMapper.update(department);
    }

    public void remove(String sn) {
        departmentMapper.delete(sn);
    }

    public Department get(String sn) {
        return departmentMapper.select(sn);
    }

    public List<Department> getAll() {
        return departmentMapper.selectAll();
    }
}
