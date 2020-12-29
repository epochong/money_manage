package com.imooc.mm.service.impl;

import com.imooc.mm.dao.ClaimVoucherMapper;
import com.imooc.mm.dao.ClaimVoucherItemMapper;
import com.imooc.mm.dao.DealRecordMapper;
import com.imooc.mm.dao.EmployeeMapper;
import com.imooc.mm.entity.ClaimVoucher;
import com.imooc.mm.entity.ClaimVoucherItem;
import com.imooc.mm.entity.DealRecord;
import com.imooc.mm.entity.Employee;
import com.imooc.mm.global.Contant;
import com.imooc.mm.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ClaimVoucherServiceImpl implements ClaimVoucherService {
    @Autowired
    private ClaimVoucherMapper claimVoucherMapper;
    @Autowired
    private ClaimVoucherItemMapper claimVoucherItemMapper;
    @Autowired
    private DealRecordMapper dealRecordMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(ClaimVoucher claimVoucher, List <ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherMapper.insert(claimVoucher);

        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemMapper.insert(item);
        }
    }

    @Override
    public ClaimVoucher get(int id) {
        return claimVoucherMapper.selectById(id);
    }

    @Override
    public List <ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemMapper.selectByClaimVoucher(cvid);
    }

    @Override
    public List <DealRecord> getRecords(int cvid) {
        return dealRecordMapper.selectByClaimVoucher(cvid);
    }

    @Override
    public List <ClaimVoucher> getForSelf(String sn) {
        return claimVoucherMapper.selectByCreateSn(sn);
    }

    @Override
    public List <ClaimVoucher> getForDeal(String sn) {
        return claimVoucherMapper.selectByNextDealSn(sn);
    }

    @Override
    public void update(ClaimVoucher claimVoucher, List <ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherMapper.update(claimVoucher);

        List <ClaimVoucherItem> olds = claimVoucherItemMapper.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            for (ClaimVoucherItem item : items) {
                if (item.getId().equals(old.getId())) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                claimVoucherItemMapper.deleteById(old.getId());
            }
        }
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            if (Objects.nonNull(item.getId()) && item.getId() > 0) {
                claimVoucherItemMapper.updateById(item);
            } else {
                claimVoucherItemMapper.insert(item);
            }
        }

    }

    @Override
    public void submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherMapper.selectById(id);
        Employee employee = employeeMapper.select(claimVoucher.getCreateSn());

        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        claimVoucher.setNextDealSn(employeeMapper.selectByDepartmentAndPost(employee.getDepartmentSn(), employee.getPost()).get(0).getSn());
        claimVoucherMapper.update(claimVoucher);

        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordMapper.insert(dealRecord);
    }

    @Override
    public void deal(DealRecord dealRecord) {
        ClaimVoucher claimVoucher = claimVoucherMapper.selectById(dealRecord.getClaimVoucherId());
        Employee employee = employeeMapper.select(dealRecord.getDealSn());
        dealRecord.setDealTime(new Date());
        if (dealRecord.getDealWay().equals(Contant.DEAL_PASS)) {
            if (claimVoucher.getTotalAmount() <= Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)) {
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                claimVoucher.setNextDealSn(employeeMapper.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            } else {
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeMapper.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_BACK)) {
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_REJECT)) {
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_PAID)) {
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherMapper.update(claimVoucher);
        dealRecordMapper.insert(dealRecord);
    }

}
