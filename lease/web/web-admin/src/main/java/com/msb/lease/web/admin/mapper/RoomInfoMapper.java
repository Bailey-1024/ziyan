package com.msb.lease.web.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.model.entity.RoomInfo;
import com.msb.lease.web.admin.vo.room.RoomItemVo;
import com.msb.lease.web.admin.vo.room.RoomQueryVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.msb.lease.model.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {
    /**
     * 根据条件分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);
}




