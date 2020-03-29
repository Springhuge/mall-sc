package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.response.UmsMenuNode;
import com.jihu.mall.ums.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
