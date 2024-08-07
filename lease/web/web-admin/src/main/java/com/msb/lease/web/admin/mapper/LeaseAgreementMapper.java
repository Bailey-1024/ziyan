package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.LeaseAgreement;
import com.msb.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.msb.lease.web.admin.vo.agreement.AgreementVo;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.LeaseAgreement
*/
public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    //分页查询
    IPage<AgreementVo> selectLAPage(IPage<AgreementVo> page, AgreementQueryVo queryVo);
}




