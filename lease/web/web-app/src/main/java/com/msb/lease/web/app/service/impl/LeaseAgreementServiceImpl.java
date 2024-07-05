package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msb.lease.model.entity.*;
import com.msb.lease.web.app.mapper.*;
import com.msb.lease.web.app.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.agreement.AgreementDetailVo;
import com.msb.lease.web.app.vo.graph.GraphVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    @Resource
    private LeaseAgreementMapper leaseAgreementMapper;
    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;
    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Resource
    private PaymentTypeMapper paymentTypeMapper;
    @Resource
    private LeaseTermMapper leaseTermMapper;
    @Resource
    private GraphInfoMapper graphInfoMapper;
    @Override
    public AgreementDetailVo getDetailById(Long id) {
        //通过id获取租约信息
        LeaseAgreement leaseAgreement=leaseAgreementMapper.selectById(id);
        //创建租约详细信息
        AgreementDetailVo agreementDetailVo=new AgreementDetailVo();
        //通过租约获得公寓ID，获取公寓名称和图片列表
        ApartmentInfo apartmentInfo=apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());
        //通过租约获得房间ID，获取房间号和房间图片列表
        RoomInfo roomInfo=roomInfoMapper.selectById(leaseAgreement.getRoomId());
        //通过租约获得支付方式ID，获取支付方式信息
        PaymentType paymentType=paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        //通过租约获取租期ID,获取租期信息
        LeaseTerm leaseTerm=leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());
        //通过公寓号获取公寓图片列表
        //创建条件选择器
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper1.eq(GraphInfo::getItemId,apartmentInfo.getId()).eq(GraphInfo::getItemType,1);
        List<GraphInfo> apartmentList=graphInfoMapper.selectList(graphInfoLambdaQueryWrapper1);
        List<GraphVo> apartmentGraphs=new ArrayList<>();
        GraphVo apartmentGraph= new GraphVo();
        for (int i = 0; i < apartmentList.size(); i++) {
            apartmentGraph.setName(apartmentList.get(i).getName());
            apartmentGraph.setUrl(apartmentList.get(i).getUrl());
            apartmentGraphs.add(apartmentGraph);
        }
        //通过房间号获取房间图片列表
        //创建条件选择器
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper2.eq(GraphInfo::getItemId,roomInfo.getId()).eq(GraphInfo::getItemType,2);
        List<GraphInfo> roomList=graphInfoMapper.selectList(graphInfoLambdaQueryWrapper2);
        List<GraphVo> roomGraphs=new ArrayList<>();
        GraphVo roomGraph= new GraphVo();
        for (int i = 0; i < roomList.size(); i++) {
            roomGraph.setName(roomList.get(i).getName());
            roomGraph.setUrl(roomList.get(i).getUrl());
            roomGraphs.add(roomGraph);
        }
        //拷贝租约信息到租约详细信息中(给数据的，收数据的)
        BeanUtils.copyProperties(leaseAgreement,agreementDetailVo);
        //设置公寓名称
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        //设置房间号
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        //设置支付方式
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        //设置租期月份
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        //设置租期单位
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());
        //设置公寓图片列表
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphs);
        //设置房间图片列表
        agreementDetailVo.setRoomGraphVoList(roomGraphs);
        return agreementDetailVo;
    }
}




