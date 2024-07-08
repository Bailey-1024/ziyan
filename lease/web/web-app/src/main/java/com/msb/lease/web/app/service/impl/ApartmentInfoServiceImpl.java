package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.lease.model.entity.ApartmentInfo;
import com.msb.lease.model.entity.FacilityInfo;
import com.msb.lease.model.entity.GraphInfo;
import com.msb.lease.model.entity.LabelInfo;
import com.msb.lease.web.app.mapper.ApartmentInfoMapper;
import com.msb.lease.web.app.mapper.FacilityInfoMapper;
import com.msb.lease.web.app.mapper.GraphInfoMapper;
import com.msb.lease.web.app.mapper.LabelInfoMapper;
import com.msb.lease.web.app.service.ApartmentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.msb.lease.web.app.vo.apartment.ApartmentItemVo;
import com.msb.lease.web.app.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    //根据获取公寓信息
    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        //通过公寓id查询图片信息
        List<GraphVo> graphVoList = graphInfoMapper.selectByListId(id);
        //通过公寓id查询标签信息列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectByListIds(id);
        //通过id获取配套列表信息
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectByList(id);
        //获取房间最小租金
        BigDecimal bigDecimal = graphInfoMapper.selectMin();
        //查询ApartmentInfo对象
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setMinRent(bigDecimal);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        BeanUtils.copyProperties(apartmentInfo, apartmentDetailVo);
        return apartmentDetailVo;
    }

    //通过公寓id查询公寓信息
    @Override
    public ApartmentItemVo selectByListId(Long id) {
        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();

        //1.通过公寓id查询标签信息列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectByListIds(id);
        //2.通过公寓id查询ApartmentInfo对象
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        //3.通过公寓id查询图片信息
        List<GraphVo> graphVoList = graphInfoMapper.selectByListId(id);
        //4.获取房间最小租金
        BigDecimal bigDecimal = graphInfoMapper.selectMin();

        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        apartmentItemVo.setLabelInfoList(labelInfoList);
        apartmentItemVo.setGraphVoList(graphVoList);
        apartmentItemVo.setMinRent(bigDecimal);
        return apartmentItemVo;
    }
}




