package com.msb.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.model.entity.LabelInfo;
import com.msb.lease.web.admin.service.LabelInfoService;
import com.msb.lease.web.admin.mapper.LabelInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class LabelInfoServiceImpl extends ServiceImpl<LabelInfoMapper, LabelInfo>
    implements LabelInfoService{
    @Resource
    private LabelInfoMapper labelInfoMapper;
    /**
     * 根据公寓id查询对应的标签信息
     * @param id
     * @return
     */
    @Override
    public List<LabelInfo> selectListByApartmentId(Long id) {
        return labelInfoMapper.selectListByApartmentId(id);
    }
}




