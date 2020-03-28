package com.jihu.mall.ums.controller;

import com.jihu.mall.auth.service.UmsAdminService;
import com.jihu.mall.common.api.CommonPage;
import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.model.UmsAdmin;
import com.jihu.mall.ums.model.UmsRole;
import com.jihu.mall.ums.model.request.UmsAdminLoginParam;
import com.jihu.mall.ums.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = "UmsAdminController" , description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/info")
    public CommonResult getAdminInfo(Principal principal){
        if(principal == null){
            return  CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin admin = adminService.getAdminByUsername(username);
        if(Optional.ofNullable(admin).isPresent()){
            Map<String, Object> data = new HashMap<>();
            data.put("username",username);
            data.put("roles", new String[]{"TEST"});
            data.put("menus",roleService.getMenuList(admin.getId()));
            data.put("icon", admin.getIcon());
            return CommonResult.success(data);
        }
      return CommonResult.failed("用户名已不存在");
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){

        List<UmsAdmin> umsAdmins = adminService.list(keyword,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(umsAdmins));
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable long id,@RequestBody UmsAdmin admin){
        int count = adminService.update(id, admin);
        if(count > 0 ){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        int count = adminService.delete(id);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public CommonResult getRoleList(@PathVariable("adminId") Long adminId){
        List<UmsRole> roles = adminService.getRoleList(adminId);
        return CommonResult.success(roles);
    }

    @ApiOperation("修改指定用户角色")
    @PostMapping("/role/update")
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                     @RequestParam("roleIds") List<Long> roleIds){
        int count = adminService.updateRole(adminId,roleIds);
        if(count >= 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable("id") Long id,
                                     @RequestParam(value = "status") Integer status){
        int count = adminService.updateStatus(id,status);
        if(count > 0 ){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody UmsAdmin umsAdmin){
        UmsAdmin resutlt = adminService.register(umsAdmin);
        if(Optional.ofNullable(resutlt).isPresent()){
            return CommonResult.success(resutlt);
        }
        return CommonResult.failed();
    }

}
