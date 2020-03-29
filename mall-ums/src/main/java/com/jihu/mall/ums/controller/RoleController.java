package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonPage;
import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.UmsResource;
import com.jihu.mall.ums.model.UmsRole;
import com.jihu.mall.ums.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        List<UmsRole> umsRoles = roleService.list(keyword,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(umsRoles));
    }

    @ApiOperation("添加角色")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsRole umsRole){
        int count = roleService.create(umsRole);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable("id") Long id,
                                 @RequestBody UmsRole role){
        int count = roleService.update(id,role);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids){
        int count = roleService.delete(ids);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取角色菜单")
    @GetMapping("/listMenu/{roleId}")
    public CommonResult listMenu(@PathVariable("roleId") Long roleId){
        List<UmsMenu> menus = roleService.listMenu(roleId);
        return CommonResult.success(menus);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping("/allocMenu")
    public CommonResult allocMenu(@RequestParam("roleId") Long roleId,
                                    @RequestParam("menuIds") List<Long> menuIds){
        int count = roleService.allocMenu(roleId,menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult listResource(@PathVariable Long roleId){
        List<UmsResource> roleList = roleService.listResource(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource")
    public CommonResult allocResource(@RequestParam("roleId") Long roleId,
                                        @RequestParam("resourceIds") List<Long> resourceIds){
        int count = roleService.allocResource(roleId,resourceIds);
        return CommonResult.success(count);
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable("id") Long roleId,
                                       @RequestParam("status") Integer status){
        int count = roleService.updateStatus(roleId,status);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
