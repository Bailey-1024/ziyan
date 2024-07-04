package com.msb.lease.web.admin.service;

import com.msb.lease.model.entity.LeaseAgreement;
import com.msb.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.msb.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    //分页查询
    IPage<AgreementVo> selectLAPage(IPage<AgreementVo> page, AgreementQueryVo queryVo);
}
