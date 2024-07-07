package com.msb.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.lease.model.entity.FacilityInfo;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {
    /**
     * 根据公寓id查询公寓配套列表
     * @param id
     * @return
     */
    List<FacilityInfo> selectListByApartmentId(Long id);

    /**
     * 根据房间id查询房间配套列表
     * @param id
     * @return
     */
    List<FacilityInfo> selectListByRoomId(Long id);
}




