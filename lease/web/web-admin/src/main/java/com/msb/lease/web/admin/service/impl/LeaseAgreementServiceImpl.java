package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.LeaseAgreement;
import com.msb.lease.web.admin.mapper.LeaseAgreementMapper;
import com.msb.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.msb.lease.web.admin.vo.agreement.AgreementVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Resource
    private LeaseAgreementMapper mapper;
    //分页查询
    @Override
    public IPage<AgreementVo> selectLAPage(IPage<AgreementVo> page, AgreementQueryVo queryVo) {
        return mapper.selectLAPage(page,queryVo);
    }
}




