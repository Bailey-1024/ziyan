package com.msb.lease.web.app.mapper;

import com.msb.lease.model.entity.LabelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.msb.lease.model.entity.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {
    //查询标签信息列表
    List<LabelInfo> selectByListId(Long id);
   // 通过公寓id查询标签信息列表
    List<LabelInfo> selectByListIds(Long id);
}




