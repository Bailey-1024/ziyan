package com.msb.lease.web.admin.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.msb.lease.model.entity.LeaseAgreement;
import com.msb.lease.model.enums.LeaseStatus;
import com.msb.lease.web.admin.service.LeaseAgreementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
//定时任务
@Component
public class ScheduledTask {

    @Resource
    private LeaseAgreementService service;
    //检查签约状态是否过期
    public void checkLeaseStatus(){
        //根据到期时间修改签约状态
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        //当到期时间小于当前时间时
        updateWrapper.le(LeaseAgreement::getLeaseEndDate, new Date());
        //当签约为已签约和续约待确定时才会需要检查签约状态是否过期
        updateWrapper.in(LeaseAgreement::getStatus,LeaseStatus.SIGNED,LeaseStatus.WITHDRAWING);
        //修改状态为已到期（枚举中EXPIRED）
        updateWrapper.set(LeaseAgreement::getStatus,LeaseStatus.EXPIRED);
        service.update(updateWrapper);
    }
}
