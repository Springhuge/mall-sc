package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsResourceCategory;
import com.jihu.mall.ums.service.ResourceCategoryService;
import com.jihu.mall.ums.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "ResourceCategoryController",description = "后台资源分类管理")
@RestController
@RequestMapping("/resourceCategory")
public class ResourceCategoryController {

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有后台分类")
    @GetMapping("/listAll")
    public CommonResult listAll(){
        List<UmsResourceCategory>  resourceCategories = resourceCategoryService.listAll();
        return CommonResult.success(resourceCategories);
    }
}
