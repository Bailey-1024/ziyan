package com.msb.lease.web.admin.service;

import com.msb.lease.model.entity.LabelInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LabelInfoService extends IService<LabelInfo> {
    /**
     * 根据公寓id查询对应的标签信息
     * @param id
     * @return
     */
    List<LabelInfo> selectListByApartmentId(Long id);
}
