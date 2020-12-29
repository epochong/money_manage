package com.imooc.mm.dao;

import com.imooc.mm.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimVoucherMapper {
    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void deleteById(int id);

    ClaimVoucher selectById(int id);

    List <ClaimVoucher> selectByCreateSn(String csn);

    List <ClaimVoucher> selectByNextDealSn(String ndsn);
}
