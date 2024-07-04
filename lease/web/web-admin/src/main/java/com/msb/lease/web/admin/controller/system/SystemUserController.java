package com.msb.lease.web.admin.controller.system;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msb.lease.common.result.Result;
import com.msb.lease.model.entity.SystemUser;
import com.msb.lease.model.enums.BaseStatus;
import com.msb.lease.web.admin.service.SystemUserService;
import com.msb.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.msb.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "后台用户信息管理")
@RestController
@RequestMapping("/admin/system/user")
public class SystemUserController {
    @Autowired
    private SystemUserService userService;

    @Operation(summary = "根据条件分页查询后台用户列表")
    @GetMapping("page")
    public Result<IPage<SystemUserItemVo>> page(@RequestParam long current, @RequestParam long size, SystemUserQueryVo queryVo) {
        //mybatsi的分页
        IPage<SystemUser> iPage = new Page<>(current, size);
        //调用Service层的方法
        IPage<SystemUserItemVo> page = userService.selectPage(iPage, queryVo);
        return Result.ok(page);
    }

    @Operation(summary = "根据ID查询后台用户信息")
    @GetMapping("getById")
    public Result<SystemUserItemVo> getById(@RequestParam Long id) {
        //调用Service层的方法
        SystemUserItemVo systemUserItemVos = userService.selectById(id);
        return Result.ok(systemUserItemVos);
    }

    @Operation(summary = "保存或更新后台用户信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SystemUser systemUser) {
        //判断password是否不为空如果不为空我们要对password进行加密
        if (systemUser.getPassword() != null) {
            systemUser.setPassword(DigestUtils.md5Hex(systemUser.getPassword()));
        }
        //如果注解Tableld注解存在则更新记录否则插入一条记录
        userService.saveOrUpdate(systemUser);

        return Result.ok();
    }

    @Operation(summary = "判断后台用户名是否可用")
    @GetMapping("isUserNameAvailable")
    public Result<Boolean> isUsernameExists(@RequestParam String username) {
        //调用Service层的方法 返回count  通过username查询用户总数
        Integer count = userService.countByUsername(username);
        return Result.ok(count == 0);  //true 表示长度为0可以用  false 表示长度>0 表示不可以用

    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除后台用户信息")
    public Result removeById(@RequestParam Long id) {
        //调用Service层的方法
        userService.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "根据ID修改后台用户状态")
    @PostMapping("updateStatusByUserId")
    public Result updateStatusByUserId(@RequestParam Long id, @RequestParam BaseStatus status) {
        //通过mybatis自带的方法开书写 UpdateWrapper用来构造sql语句
        UpdateWrapper<SystemUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("status", status);
        userService.update(wrapper);
        return Result.ok();
    }
}
