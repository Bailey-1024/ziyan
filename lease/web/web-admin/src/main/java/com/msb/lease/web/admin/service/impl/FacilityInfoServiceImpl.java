package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.model.entity.FacilityInfo;
import com.msb.lease.web.admin.service.FacilityInfoService;
import com.msb.lease.web.admin.mapper.FacilityInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class FacilityInfoServiceImpl extends ServiceImpl<FacilityInfoMapper, FacilityInfo>
    implements FacilityInfoService{
    @Resource
    private FacilityInfoMapper facilityInfoMapper;
    /**
     * 通过公寓ID查询公寓配套列表
     * @param id
     * @return
     */
    @Override
    public List<FacilityInfo> selectListByApartmentId(Long id) {
        return facilityInfoMapper.selectListByApartmentId(id);
    }
}




