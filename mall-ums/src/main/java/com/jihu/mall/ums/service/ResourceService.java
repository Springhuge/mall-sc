package com.jihu.mall.ums.service;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.jihu.mall.ums.example.UmsResourceExample;
import com.jihu.mall.ums.mapper.UmsResourceMapper;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.UmsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("resourceService")
public class ResourceService {

    @Autowired
    private UmsResourceMapper resourceMapper;

    public List<UmsResource> listAll() {
        return resourceMapper.selectByExample(new UmsResourceExample());
    }

    public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsResourceExample example = new UmsResourceExample();
        if(categoryId != null){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            example.createCriteria().andNameLike("%"+nameKeyword+"%");
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            example.createCriteria().andUrlLike("%"+urlKeyword+"%");
        }
        return resourceMapper.selectByExample(example);
    }

    public int create(UmsResource resource) {
        resource.setCreateTime(new Date());
        return resourceMapper.insert(resource);
    }

    public int update(Long resourceId, UmsResource resource) {
        UmsResource umsResource = resourceMapper.selectByPrimaryKey(resourceId);
        if(Optional.ofNullable(umsResource).isPresent()){
            resource.setId(resourceId);
            return resourceMapper.updateByPrimaryKey(resource);
        }
        return 0;
    }


    public int delete(Long resourceId) {
        return resourceMapper.deleteByPrimaryKey(resourceId);
    }
}
