package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonPage;
import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsResource;
import com.jihu.mall.ums.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RescourseController", description = "后台资源管理")
@RestController
@RequestMapping("/resource")
public class RescourseController {

    @Autowired
    private ResourceService resourceServicere;

    @ApiOperation("查询所有后台资源")
    @GetMapping("/listAll")
    public CommonResult listAll(){
        List<UmsResource> resources = resourceServicere.listAll();
        return CommonResult.success(resources);
    }

    @ApiOperation("分页查询后台资源")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) String nameKeyword,
                               @RequestParam(required = false) String urlKeyword,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer  pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5") Integer  pageSize){
        List<UmsResource> resources = resourceServicere.list(categoryId,nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resources));
    }

    @ApiOperation("添加后台资源")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResource resource){
        int count = resourceServicere.create(resource);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改后台资源")
    @PostMapping("/update/{resourceId}")
    public CommonResult update(@PathVariable("resourceId") Long resourceId,
                                 @RequestBody UmsResource resource){
        int count = resourceServicere.update(resourceId,resource);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除后台资源")
    @PostMapping("/delete/{resourceId}")
    public CommonResult delete(@PathVariable("resourceId") Long resourceId){
        int count = resourceServicere.delete(resourceId);
        if(count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
