package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.exception.LeaseException;
import com.msb.lease.common.result.ResultCodeEnum;
import com.msb.lease.model.entity.*;
import com.msb.lease.model.enums.ItemType;
import com.msb.lease.web.admin.mapper.ApartmentInfoMapper;
import com.msb.lease.web.admin.mapper.RoomInfoMapper;
import com.msb.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.msb.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.msb.lease.web.admin.vo.fee.FeeValueVo;
import com.msb.lease.web.admin.vo.graph.GraphVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private LabelInfoService labelInfoService;

    @Resource
    private FacilityInfoService facilityInfoService;

    @Resource
    private FeeValueService feeValueService;

    @Resource
    private RoomInfoMapper roomInfoMapper;

    @Resource
    private ProvinceInfoService provinceInfoService;
    @Resource
    private CityInfoService cityInfoService;
    @Resource
    private DistrictInfoService districtInfoService;

    /**
     * 保存或者更新公寓信息
     *
     * @param apartmentSubmitVo
     */
    @Override
    @Transactional
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {

        //判断是更新还是新增操作
        boolean isUpdate = apartmentSubmitVo.getId() != null;
        //调用父类的保存和更新方法，服务与表ApartmentInfo对表ApartmentSubmitVo没有影响，不会处理多余的字段
        //如果是保存，则先对公寓相关信息插入表中--ID的回显--根据获得的ID对配套信息进行新增
        //如果是更新，则先对公寓相关信息更新--在删除配套的信息--根据ID在新增配套的信息

        //打补丁
        Long districtId = apartmentSubmitVo.getDistrictId();//所在区县id
        String districtName = districtInfoService.getById(districtId).getName();
        Long cityId = apartmentSubmitVo.getCityId();//所在城市id
        String cityName = cityInfoService.getById(cityId).getName();
        Long provinceId = apartmentSubmitVo.getProvinceId();//所在省份id
        String provinceName = provinceInfoService.getById(provinceId).getName();
        apartmentSubmitVo.setDistrictName(districtName);
        apartmentSubmitVo.setCityName(cityName);
        apartmentSubmitVo.setProvinceName(provinceName);


        super.saveOrUpdate(apartmentSubmitVo);
        if (isUpdate) {
            //更新操作---先把原有的配套信息都删除掉，在进行增加

            //1.删除图片
            LambdaQueryWrapper<GraphInfo> wrapper = new LambdaQueryWrapper<>();
            //图片关联的类型
            wrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            //图片关联的外键id
            wrapper.eq(apartmentSubmitVo.getId() != null, GraphInfo::getItemId, apartmentSubmitVo.getId());
            graphInfoService.remove(wrapper);
            //2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);
            //3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);
            //4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);
        }


        //1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)) {
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
        if (!CollectionUtils.isEmpty(facilityInfoIds)) {
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
        if (!CollectionUtils.isEmpty(labelIds)) {
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabels.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }

        //4.插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValues.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }

    }

    /**
     * 根据条件分页查询公寓列表
     * 多条件分页查询
     *
     * @param apartmentItemVos
     * @param queryVo
     * @return
     */
    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQuery(Page<ApartmentItemVo> apartmentItemVos, ApartmentQueryVo queryVo) {
        //先去ApartmentInfo表中查询去公寓列表数据，然后分别计算每个公寓的房间总数和空闲房间数
        //空闲房间数不好算，可以算不空闲房间总数，房间-签约表
        return apartmentInfoMapper.pageApartmentByQuery(apartmentItemVos, queryVo);
    }

    /**
     * 根据Id获取公寓的详细信息
     *
     * @param id
     * @return
     */

    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        //1.查询ApartmentInfo公寓信息
//        ApartmentInfo apartmentInfo = this.getById(id);
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);


        //如果有数据，则继续查询
        if (apartmentInfo == null) {
            return null;
        }

        //2.查询GraphInfo
        List<GraphVo> graphVoList =
                graphInfoService.selectListByItemTypeAndId(ItemType.APARTMENT, id);
        //3.查询LabelInfo
        List<LabelInfo> labelInfoList =
                labelInfoService.selectListByApartmentId(id);
        //4.查询FacilityInfo
        List<FacilityInfo> facilityInfoList =
                facilityInfoService.selectListByApartmentId(id);
        //5.查询FeeValue
        List<FeeValueVo> feeValueVoList =
                feeValueService.selectListByApartmentId(id);

        ApartmentDetailVo adminApartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo, adminApartmentDetailVo);
        adminApartmentDetailVo.setGraphVoList(graphVoList);
        adminApartmentDetailVo.setLabelInfoList(labelInfoList);
        adminApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        adminApartmentDetailVo.setFeeValueVoList(feeValueVoList);
        return adminApartmentDetailVo;

    }

    /**
     * 根据iD删除公寓的配套信息
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeApartmentById(Long id) {
        LambdaQueryWrapper<RoomInfo> roomQueryWrapper = new LambdaQueryWrapper<>();
        roomQueryWrapper.eq(RoomInfo::getApartmentId, id);
        Long count = roomInfoMapper.selectCount(roomQueryWrapper);
        if (count > 0) {
            //代表当前公寓还有房间，所以不能删除
//            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }
        //调用父类的相关方法，先删除公寓对应的信息
        super.removeById(id);
        //1.删除GraphInfo
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new
                LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphQueryWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphQueryWrapper);
        //2.删除ApartmentLabel
        LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper = new
                LambdaQueryWrapper<>();
        labelQueryWrapper.eq(ApartmentLabel::getApartmentId, id);
        apartmentLabelService.remove(labelQueryWrapper);
        //3.删除ApartmentFacility
        LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper = new
                LambdaQueryWrapper<>();
        facilityQueryWrapper.eq(ApartmentFacility::getApartmentId, id);
        apartmentFacilityService.remove(facilityQueryWrapper);
        //4.删除ApartmentFeeValue
        LambdaQueryWrapper<ApartmentFeeValue> feeQueryWrapper = new
                LambdaQueryWrapper<>();
        feeQueryWrapper.eq(ApartmentFeeValue::getApartmentId, id);
        apartmentFeeValueService.remove(feeQueryWrapper);
    }
}




