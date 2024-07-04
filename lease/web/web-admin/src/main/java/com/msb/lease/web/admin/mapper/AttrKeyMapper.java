package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.lease.model.entity.AttrKey;
import com.msb.lease.web.admin.vo.attr.AttrKeyVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.AttrKey
*/
public interface AttrKeyMapper extends BaseMapper<AttrKey> {
    /**
     * listAttrInfo
     * @return
     */
    List<AttrKeyVo> listAttrInfo();
}




