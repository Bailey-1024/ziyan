package com.msb.lease.web.app.controller.history;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.result.Result;
import com.msb.lease.web.app.service.BrowsingHistoryService;
import com.msb.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "浏览历史管理")
@RequestMapping("/app/history")
public class BrowsingHistoryController {

    @Resource
    private BrowsingHistoryService service;
    @Operation(summary = "获取浏览历史")
    @GetMapping("pageItem")
    private Result<IPage<HistoryItemVo>> page(@RequestParam long current, @RequestParam long size) {
       ////创建分页模板
       // IPage<HistoryItemVo> page=new Page<>(current,size);
       ////调用service方法返回数据
       // IPage<HistoryItemVo> result=service.getHistory(page);
       // return Result.ok(result);
        return null;
    }
}
