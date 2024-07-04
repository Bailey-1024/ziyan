package com.msb.lease.web.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.SystemPost;
import com.msb.lease.model.enums.BaseStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.msb.lease.web.admin.service.SystemPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "后台用户岗位管理")
@RequestMapping("/admin/system/post")
public class SystemPostController {
@Autowired
private SystemPostService postService;
    @Operation(summary = "分页获取岗位信息")
    @GetMapping("page")
    private Result<IPage<SystemPost>> page(@RequestParam long current, @RequestParam long size) {
         //mybatis分页查询创建
         IPage<SystemPost> iPage=new Page<>(current,size);
         //调用Service层写好的方法
         IPage<SystemPost> page = postService.pageByPost(iPage);

        return Result.ok(page);
    }

    @Operation(summary = "保存或更新岗位信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SystemPost systemPost) {
        //mybatisplus自动装配  如果注解Tableld注解存在则更新记录否则插入一条记录
        postService.saveOrUpdate(systemPost);
        return Result.ok();
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据id删除岗位")
    public Result removeById(@RequestParam Long id) {
     //调用Service层方法 执行删除操作
        postService.updateByidTo(id);
        return Result.ok();
    }

    @GetMapping("getById")
    @Operation(summary = "根据id获取岗位信息")
    public Result<SystemPost> getById(@RequestParam Long id) {
        //调用Service层的通过id查询用户信息的方法
        SystemPost systemPost=postService.selectById(id);
        return Result.ok(systemPost);
    }

    @Operation(summary = "获取全部岗位列表")
    @GetMapping("list")
    public Result<List<SystemPost>> list() {
        //获取所有的列表信息
        List<SystemPost> list = postService.list();
        return Result.ok(list);
    }

    @Operation(summary = "根据岗位id修改状态")
    @PostMapping("updateStatusByPostId")
    public Result updateStatusByPostId(@RequestParam Long id, @RequestParam BaseStatus status) {
        UpdateWrapper<SystemPost> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("status",status);
        postService.update(updateWrapper);
        return Result.ok();
    }
}
