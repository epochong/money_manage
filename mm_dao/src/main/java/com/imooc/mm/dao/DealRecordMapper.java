package com.imooc.mm.dao;

import com.imooc.mm.entity.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DealRecordMapper {
    void insert(DealRecord dealRecord);
    List<DealRecord> selectByClaimVoucher(int cvid);
}
