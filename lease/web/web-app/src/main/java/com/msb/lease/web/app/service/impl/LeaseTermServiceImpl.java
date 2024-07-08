package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.msb.lease.model.entity.LeaseTerm;
import com.msb.lease.model.entity.RoomLeaseTerm;
import com.msb.lease.web.app.mapper.LeaseAgreementMapper;
import com.msb.lease.web.app.mapper.LeaseTermMapper;
import com.msb.lease.web.app.mapper.RoomInfoMapper;
import com.msb.lease.web.app.mapper.RoomLeaseTermMapper;
import com.msb.lease.web.app.service.LeaseAgreementService;
import com.msb.lease.web.app.service.LeaseTermService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_term(租期)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
        implements LeaseTermService {
    //租期信息
    @Resource
    private LeaseTermMapper leaseTermMapper;
    @Resource
    //房间-租期信息
    private RoomLeaseTermMapper roomLeaseTermMapper;

    @Override
    public List<LeaseTerm> getLeaseTerm(Long id) {
        //创建条件选择器
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //传入房间id
        roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId,id);
        //使用房间-租期mapper通过房间id获得房间租期表信息
        List<RoomLeaseTerm> roomLeaseTerms = roomLeaseTermMapper.selectList(roomLeaseTermLambdaQueryWrapper);
        //创建集合
        List<LeaseTerm> leaseTerms =new ArrayList<>();
        //循环房间租期表信息
        for (int i = 0; i < roomLeaseTerms.size(); i++) {
            //获取租期表id
            Long leaseTermId = roomLeaseTerms.get(i).getLeaseTermId();
            //通过租期表id获得租期表信息
            LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseTermId);
            leaseTerms.add(leaseTerm);
        }
        return leaseTerms;
    }
}




