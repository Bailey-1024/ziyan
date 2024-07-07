package com.msb.lease.web.app.controller.room;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.result.Result;
import com.msb.lease.web.app.service.RoomInfoService;
import com.msb.lease.web.app.vo.room.RoomDetailVo;
import com.msb.lease.web.app.vo.room.RoomItemVo;
import com.msb.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.simpleframework.xml.ElementArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "房间信息")
@RestController
@RequestMapping("/app/room")
public class RoomController {
    @Autowired
    private RoomInfoService roomInfoService;

    @Operation(summary = "分页查询房间列表")
    @GetMapping("pageItem")
    // RoomQueryVo 查询条件 就是页面上面的四个查询条件
    public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo) {
        //创建IPage的对象
        IPage<RoomQueryVo> iPage = new Page<>(current, size);
        //调用Service层的方法
        IPage<RoomItemVo> list = roomInfoService.iPageByquerVo(iPage, queryVo);
        return Result.ok(list);
    }

    @Operation(summary = "根据id获取房间的详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam Long id) {
        //调用service层的方法返回一个RoomDetailVo对象
        RoomDetailVo roomDetailVo = roomInfoService.selectById(id);
        return Result.ok(roomDetailVo);
    }

    @Operation(summary = "根据公寓id分页查询房间列表")
    @GetMapping("pageItemByApartmentId")
    public Result<IPage<RoomItemVo>> pageItemByApartmentId(@RequestParam long current, @RequestParam long size, @RequestParam Long id) {
        //开启分页
        IPage<RoomQueryVo> iPage = new Page<>(current, size);
        //调用Service层的方法
        IPage<RoomItemVo> itemVoIPage=roomInfoService.selectByIPage(iPage,id);
        return Result.ok(itemVoIPage);
    }
}
