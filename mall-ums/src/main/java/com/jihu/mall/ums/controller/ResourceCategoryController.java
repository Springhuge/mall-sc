package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsResource;
import com.jihu.mall.ums.model.UmsResourceCategory;
import com.jihu.mall.ums.service.ResourceCategoryService;
import com.jihu.mall.ums.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("添加后台资源分类")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResourceCategory resourceCategory){
        int count = resourceCategoryService.create(resourceCategory);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除后台资源分类")
    @PostMapping("/delete/{resourceCategoryId}")
    public CommonResult delete(@PathVariable("resourceCategoryId") Long resourceCategoryId){
        int count = resourceCategoryService.delete(resourceCategoryId);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改后台资源分类")
    @PostMapping("/update/{resourceCategoryId}")
    public CommonResult update(@PathVariable("resourceCategoryId") Long resourceCategoryId,
                               @RequestBody UmsResourceCategory resourceCategory){
        int count = resourceCategoryService.update(resourceCategoryId,resourceCategory);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
