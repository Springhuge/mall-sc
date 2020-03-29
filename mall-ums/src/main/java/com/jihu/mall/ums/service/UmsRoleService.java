package com.jihu.mall.ums.service;

import com.github.pagehelper.PageHelper;
import com.jihu.mall.auth.mapper.UmsRoleMapper;
import com.jihu.mall.common.api.CommonResult;
import com.jihu.mall.ums.example.UmsRoleExample;
import com.jihu.mall.ums.example.UmsRoleMenuRelationExample;
import com.jihu.mall.ums.example.UmsRoleResourceRelationExample;
import com.jihu.mall.ums.mapper.UmsMenuMapper;
import com.jihu.mall.ums.mapper.UmsRoleMenuRelationMapper;
import com.jihu.mall.ums.mapper.UmsRoleResourceRelationMapper;
import com.jihu.mall.ums.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("umsRoleService")
public class UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;

    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;

    public List<UmsMenu> getMenuList(Long adminId){
        return roleMapper.getMenuList(adminId);
    }

    public List<UmsRole> listAll() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    public List<UmsRole> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        UmsRoleExample example = new UmsRoleExample();
        if(!StringUtils.isEmpty(keyword)){
            example.createCriteria().andNameLike("%"+keyword+"%");
        }
        List<UmsRole> roles = roleMapper.selectByExample(example);
        return roles;
    }

    public int create(UmsRole umsRole) {
        umsRole.setCreateTime(new Date());
        umsRole.setAdminCount(0);
        umsRole.setSort(0);
        return roleMapper.insert(umsRole);
    }

    public int update(Long id, UmsRole role) {
        UmsRole umsRole = roleMapper.selectByPrimaryKey(id);
        if(Optional.ofNullable(umsRole).isPresent()){
            role.setId(id);
            return roleMapper.updateByPrimaryKey(role);
        }
        return 0;
    }

    public int delete(List<Long> ids) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        return  roleMapper.deleteByExample(example);
    }

    public List<UmsMenu> listMenu(Long roleId) {
        return roleMapper.getMenuListByRoleId(roleId);
    }

    @Transactional
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        UmsRoleMenuRelationExample example = new UmsRoleMenuRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleMenuRelationMapper.deleteByExample(example);

        //建立新的关系
        menuIds.forEach(menuId ->{
            UmsRoleMenuRelation roleMenuRelation = new UmsRoleMenuRelation();
            roleMenuRelation.setMenuId(menuId);
            roleMenuRelation.setRoleId(roleId);
            roleMenuRelationMapper.insert(roleMenuRelation);
        });
        return menuIds.size();
    }

    public List<UmsResource> listResource(Long roleId) {
        return roleMapper.getResourceListByRoleId(roleId);
    }

    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除所有关系
        UmsRoleResourceRelationExample example = new UmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);

        //建立新的关系
        resourceIds.forEach(resourceId ->{
            UmsRoleResourceRelation roleResourceRelation = new UmsRoleResourceRelation();
            roleResourceRelation.setResourceId(resourceId);
            roleResourceRelation.setRoleId(roleId);
            roleResourceRelationMapper.insert(roleResourceRelation);
        });

        return resourceIds.size();
    }


    public int updateStatus(Long roleId, Integer status) {
        //先查询角色是否存在
        UmsRole role = roleMapper.selectByPrimaryKey(roleId);
        if(Optional.ofNullable(role).isPresent()){
            role.setStatus(status);
            return roleMapper.updateByPrimaryKey(role);
        }
        return 0;
    }
}
