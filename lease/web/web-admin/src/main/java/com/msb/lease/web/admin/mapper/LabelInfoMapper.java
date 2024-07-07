package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.lease.model.entity.LabelInfo;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {
    /**
     * 根据公寓Id查询对应的标签信息
     * @param id
     * @return
     */
    List<LabelInfo> selectListByApartmentId(Long id);

    /**
     * 根据房间ID查询标签列表
     * @param id
     * @return
     */
    List<LabelInfo> selectListByRoomId(Long id);
}




