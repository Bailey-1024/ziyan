package com.msb.lease.web.admin.service;

import com.msb.lease.model.entity.RoomInfo;
import com.msb.lease.web.admin.vo.room.RoomDetailVo;
import com.msb.lease.web.admin.vo.room.RoomItemVo;
import com.msb.lease.web.admin.vo.room.RoomQueryVo;
import com.msb.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {
    /**
     * 保存或者更新房间信息
     * @param roomSubmitVo
     */
    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);

    /**
     *根据条件分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */

    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id获取房间的详细信息
     * @param id
     * @return
     */
    RoomDetailVo getRoomDetailById(Long id);

    /**
     * 根据id删除房间配套信息
     * @param id
     */
    void removeRoomById(Long id);
}
