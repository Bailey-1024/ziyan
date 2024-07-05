package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.msb.lease.model.entity.*;
import com.msb.lease.model.enums.ItemType;
import com.msb.lease.web.admin.mapper.ApartmentInfoMapper;
import com.msb.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.msb.lease.web.admin.vo.graph.GraphVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {
    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;

    @Resource
    private GraphInfoService graphInfoService;

    @Resource
    private ApartmentLabelService apartmentLabelService;

    @Resource
    private ApartmentFacilityService apartmentFacilityService;

    @Resource
    private ApartmentFeeValueService apartmentFeeValueService;
    /**
     * 保存或者更新公寓信息
     * @param apartmentSubmitVo
     */
    @Override
    @Transactional
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
//        apartmentInfoMapper.saveOrUpdateApartment(apartmentSubmitVo);
        //判断是更新还是新增操作
        boolean isUpdate = apartmentSubmitVo.getId()!=null;
        //调用父类的保存和更新方法，服务与表ApartmentInfo对表ApartmentSubmitVo没有影响，不会处理多余的字段
        //如果是保存，则先对公寓相关信息插入表中--ID的回显--根据获得的ID对配套信息进行新增
        //如果是更新，则先对公寓相关信息更新--在删除配套的信息--根据ID在新增配套的信息
        super.saveOrUpdate(apartmentSubmitVo);
        if (isUpdate){
            //更新操作---先把原有的配套信息都删除掉，在进行增加

            //1.删除图片
            LambdaQueryWrapper<GraphInfo> wrapper = new LambdaQueryWrapper<>();
            //图片关联的类型
            wrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            //图片关联的外键id
            wrapper.eq(apartmentSubmitVo.getId()!=null,GraphInfo::getItemId,apartmentSubmitVo.getId());
            graphInfoService.remove(wrapper);
            //2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);
            //3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);
            //4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);
        }


        //1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)){
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for (GraphVo graphVo : apartmentSubmitVo.getGraphVoList()) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }
        //2.插入配套列表
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIds)){
            ArrayList<ApartmentFacility> facilityInfos = new ArrayList<>();
            for (Long facilityId : facilityInfoIds) {
                ApartmentFacility apartmentFacility = new ApartmentFacility();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityId);
                facilityInfos.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(facilityInfos);
        }
        //3.插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIds)){
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for (Long labelId:labelIds) {
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabels.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }

        //4.插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)){
            ArrayList<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for (Long feeValueId:feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValues.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }

    }
}




