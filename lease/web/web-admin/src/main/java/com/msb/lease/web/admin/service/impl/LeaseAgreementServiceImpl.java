package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.*;
import com.msb.lease.web.admin.mapper.*;
import com.msb.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.msb.lease.web.admin.vo.agreement.AgreementVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;
    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Resource
    private PaymentTypeMapper paymentTypeMapper;
    @Resource
    private LeaseTermMapper leaseTermMapper;
    //分页查询
    @Override
    public IPage<AgreementVo> selectLAPage(IPage<AgreementVo> page, AgreementQueryVo queryVo) {
        return mapper.selectLAPage(page,queryVo);
    }

    //根据ID查询租约信息
    @Override
    public AgreementVo getLessAgreementById(Long id) {
        //通过ID获取到租约信息
        LeaseAgreement leaseAgreement = mapper.selectById(id);
        //通过获取公寓id获取公寓信息
        ApartmentInfo apartmentInfo=apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());
        //通过获取房间id获取房间信息
        RoomInfo roomInfo=roomInfoMapper.selectById(leaseAgreement.getRoomId());
        //通过获取支付方式id获取支付方式
        PaymentType paymentType=paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        //通过获取租期id获取租期
        LeaseTerm leaseTerm=leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());
        //创建租约信息vo
        AgreementVo agreementVo=new AgreementVo();
        //设置值
        BeanUtils.copyProperties(leaseAgreement, agreementVo);
        agreementVo.setApartmentInfo(apartmentInfo);
        agreementVo.setRoomInfo(roomInfo);
        agreementVo.setPaymentType(paymentType);
        agreementVo.setLeaseTerm(leaseTerm);
        //返会租期信息
        return agreementVo;
    }
}




