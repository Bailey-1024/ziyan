package com.msb.lease.web.app.mapper;

import com.msb.lease.model.entity.GraphInfo;
import com.msb.lease.model.enums.ItemType;
import com.msb.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.msb.lease.model.entity.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long id);
   //通过公寓id查询图片信息
    List<GraphVo> selectByListId(Long id);
    //获取房间最小租金
    BigDecimal selectMin();

}




