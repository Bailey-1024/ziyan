package com.msb.lease.web.app.service;

import com.msb.lease.model.entity.LeaseAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.lease.web.app.vo.agreement.AgreementDetailVo;
import com.msb.lease.web.app.vo.agreement.AgreementItemVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {
    //通过id查询租约详细信息
    AgreementDetailVo getDetailById(Long id);
    //获取个人租约信息表
    List<AgreementItemVo> getItemByUserId(long userId);


}
