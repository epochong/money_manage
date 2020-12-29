package com.imooc.mm.dao;

import com.imooc.mm.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClaimVoucherItemMapper {
    void insert(ClaimVoucherItem claimVoucherItem);
    void updateById(ClaimVoucherItem claimVoucherItem);
    void deleteById(int id);
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
