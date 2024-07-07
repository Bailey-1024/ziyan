package com.msb.lease.web.app.mapper;

import com.msb.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【facility_info(配套信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.msb.lease.model.entity.FacilityInfo
*/
public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {
   //查询配套信息
    List<FacilityInfo> selectByListId(Long id);
     //通过id获取配套列表信息
    List<FacilityInfo> selectByList(Long id);
}




