package com.imooc.mm.dao;

import com.imooc.mm.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
    void insert(Employee employee);
    void update(Employee employee);
    void delete(String sn);
    Employee select(String sn);
    List<Employee> selectAll();
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn,@Param("post") String post);
}
