package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonPage;
import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsRole;
import com.jihu.mall.ums.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台用户管理角色
 */
@Api(tags = "RoleController", description = "后台用户角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult listAll(){
        List<UmsRole> roleList = roleService.listAll();
        return CommonResult.success(roleList);
    }

    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){

        List<UmsRole> umsRoles = roleService.list(keyword,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(umsRoles));
    }
}
