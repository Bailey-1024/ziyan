package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.model.entity.GraphInfo;
import com.msb.lease.model.enums.ItemType;
import com.msb.lease.web.admin.service.GraphInfoService;
import com.msb.lease.web.admin.mapper.GraphInfoMapper;
import com.msb.lease.web.admin.vo.graph.GraphVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class GraphInfoServiceImpl extends ServiceImpl<GraphInfoMapper, GraphInfo>
    implements GraphInfoService{
    @Resource
    private GraphInfoMapper graphInfoMapper;

    /**
     * 根据图像所属类型和图像所属id,查询图片列表
     *      * @param itemType
     * @param itemType
     * @param id
     * @return
     */
    @Override
    public List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long id) {
        return graphInfoMapper.selectListByItemTypeAndId( itemType,  id);
    }
}




