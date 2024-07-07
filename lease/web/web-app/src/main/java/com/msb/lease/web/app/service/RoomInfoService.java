package com.msb.lease.web.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.RoomInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.lease.web.app.vo.room.RoomItemVo;
import com.msb.lease.web.app.vo.room.RoomQueryVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {
    //分页条件查询
    IPage<RoomItemVo> iPageByquerVo(IPage<RoomQueryVo> iPage, RoomQueryVo queryVo);
}
