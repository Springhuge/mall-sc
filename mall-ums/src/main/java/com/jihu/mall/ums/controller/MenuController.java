package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonPage;
import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.response.UmsMenuNode;
import com.jihu.mall.ums.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "MenuController",description = "后台菜单管理")
@RequestMapping("/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping("/treeList")
    public CommonResult getAllMenu(){
        List<UmsMenuNode> menuNodes = menuService.treeList();
        return CommonResult.success(menuNodes);
    }

    @ApiOperation("分页查询后台菜单")
    @GetMapping("/list/{parentId}")
    public CommonResult list(@PathVariable("parentId") Long parentId,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer  pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5") Integer  pageSize){
        List<UmsMenu> menus = menuService.list(parentId,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(menus));
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping("/{id}")
    public CommonResult getItem(@PathVariable("id") Long id){
        UmsMenu menu = menuService.getItem(id);
        return CommonResult.success(menu);
    }

    @ApiOperation("添加后台菜单")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsMenu menu){
        int count = menuService.create(menu);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("/修改后台菜单")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable("id") Long menuId,@RequestBody UmsMenu menu){
        int count = menuService.update(menuId, menu);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据id删除菜单")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        int count = menuService.delete(id);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改菜单显示状态")
    @PostMapping("/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable("id") Long id,
                                     @RequestParam("hidden") Integer hidden){
        int count = menuService.updateHidden(id,hidden);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
