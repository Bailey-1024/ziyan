package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.common.login.LoginUserHolder;
import com.msb.lease.model.entity.*;
import com.msb.lease.model.enums.ItemType;
import com.msb.lease.web.app.mapper.*;
import com.msb.lease.web.app.service.ApartmentInfoService;
import com.msb.lease.web.app.service.BrowsingHistoryService;
import com.msb.lease.web.app.service.RoomInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.apartment.ApartmentItemVo;
import com.msb.lease.web.app.vo.attr.AttrValueVo;
import com.msb.lease.web.app.vo.fee.FeeValueVo;
import com.msb.lease.web.app.vo.graph.GraphVo;
import com.msb.lease.web.app.vo.room.RoomDetailVo;
import com.msb.lease.web.app.vo.room.RoomItemVo;
import com.msb.lease.web.app.vo.room.RoomQueryVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
   //浏览历史
    @Resource
    private BrowsingHistoryService browsingHistoryService;
    //分页条件查询
    @Override
    public IPage<RoomItemVo> iPageByquerVo(IPage<RoomQueryVo> iPage, RoomQueryVo queryVo) {
        //调用Mapper层的分页条件查询方法
        return roomInfoMapper.iPageByquerVo(iPage, queryVo);
    }

    //通过id查询房间详细信息
    @Override
    public RoomDetailVo selectById(Long id) {
        RoomDetailVo detailVo = new RoomDetailVo();
        //1.查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(id);
        if (roomInfo == null) {
            return null;
        }
        //2.查询图片信息列表
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);
        //3.查询属性信息列表
        List<AttrValueVo> attrValueVoList = attrValueMapper.selectByListId(id);
        //4.查询配套列表
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectByListId(id);
        //5.查询标签信息列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectByListId(id);
        //6.查询房租列表
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectListById(id);
        //7.查询支付方式列表
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectByListId(id);
        //8.查询学杂费列表
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectByListId(roomInfo.getApartmentId());
        //9.查询所属公寓信息
        ApartmentItemVo apartmentItemVo=apartmentInfoService.selectByListId(roomInfo.getApartmentId());
        //将查询所属公寓信息复制到detailVo对象当中
        BeanUtils.copyProperties(roomInfo, detailVo);
        detailVo.setApartmentItemVo(apartmentItemVo);
        detailVo.setFeeValueVoList(feeValueVoList);
        detailVo.setPaymentTypeList(paymentTypeList);
        detailVo.setLeaseTermList(leaseTermList);
        detailVo.setLabelInfoList(labelInfoList);
        detailVo.setFacilityInfoList(facilityInfoList);
        detailVo.setGraphVoList(graphVoList);
        detailVo.setAttrValueVoList(attrValueVoList);
        //保存浏览历史
        browsingHistoryService.saveHistory(LoginUserHolder.getLoginUser().getUserId(),id);
        return detailVo;
    }
   //根据公寓id分页查询房间列表
    @Override
    public IPage<RoomItemVo> selectByIPage(IPage<RoomQueryVo> iPage, Long id) {

        return roomInfoMapper.selectByIPage(iPage,id);
    }
}




