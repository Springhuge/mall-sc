package com.jihu.mall.ums.service;

import com.github.pagehelper.PageHelper;
import com.jihu.mall.auth.mapper.UmsRoleMapper;
import com.jihu.mall.ums.example.UmsRoleExample;
import com.jihu.mall.ums.mapper.UmsMenuMapper;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.UmsRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("umsRoleService")
public class UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

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
}
