package com.msb.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.RoomInfo;
import com.msb.lease.web.app.mapper.RoomInfoMapper;
import com.msb.lease.web.app.service.RoomInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.lease.web.app.vo.room.RoomItemVo;
import com.msb.lease.web.app.vo.room.RoomQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    //分页条件查询
    @Override
    public IPage<RoomItemVo> iPageByquerVo(IPage<RoomQueryVo> iPage, RoomQueryVo queryVo) {
        //调用Mapper层的分页条件查询方法
        return roomInfoMapper.iPageByquerVo(iPage,queryVo);
    }
}




