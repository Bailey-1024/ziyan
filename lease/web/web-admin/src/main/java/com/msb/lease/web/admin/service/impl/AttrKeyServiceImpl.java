package com.msb.lease.web.admin.service.impl;

import com.msb.lease.model.entity.AttrKey;
import com.msb.lease.web.admin.mapper.AttrKeyMapper;
import com.msb.lease.web.admin.service.AttrKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.admin.vo.attr.AttrKeyVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
    implements AttrKeyService{
    @Resource
    private AttrKeyMapper attrKeyMapper;
    /**
     * 查询全部属性名称和属性值列表
     * @return
     */
    @Override
    public List<AttrKeyVo> listAttrInfo() {
        return attrKeyMapper.listAttrInfo();
    }
}




