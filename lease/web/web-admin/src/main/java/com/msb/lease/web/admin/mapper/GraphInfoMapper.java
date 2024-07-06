package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.lease.model.entity.GraphInfo;
import com.msb.lease.model.enums.ItemType;
import com.msb.lease.web.admin.vo.graph.GraphVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {
    /**
     * 根据图像所属类型和图像所属id,查询图片列表
     * @param itemType
     * @param id
     * @return
     */
    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long id);
}




