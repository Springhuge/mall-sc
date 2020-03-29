package com.jihu.mall.ums.controller;

import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsResource;
import com.jihu.mall.ums.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
