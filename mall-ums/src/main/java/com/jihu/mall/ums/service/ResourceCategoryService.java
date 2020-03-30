package com.jihu.mall.ums.service;

import com.jihu.mall.ums.example.UmsResourceCategoryExample;
import com.jihu.mall.ums.example.UmsResourceExample;
import com.jihu.mall.ums.mapper.UmsResourceCategoryMapper;
import com.jihu.mall.ums.model.UmsResourceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("resourceCategoryService")
public class ResourceCategoryService {

    @Autowired
    private UmsResourceCategoryMapper resourceCategoryMapper;

    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(example);
    }

    public int create(UmsResourceCategory resourceCategory) {
        resourceCategory.setCreateTime(new Date());
        return resourceCategoryMapper.insert(resourceCategory);
    }

    public int delete(Long resourceCategoryId) {
        return resourceCategoryMapper.deleteByPrimaryKey(resourceCategoryId);
    }

    public int update(Long resourceCategoryId, UmsResourceCategory resourceCategory) {
        UmsResourceCategory umsResourceCategory = resourceCategoryMapper.selectByPrimaryKey(resourceCategoryId);
        if(Optional.ofNullable(umsResourceCategory).isPresent()){
            resourceCategory.setId(resourceCategoryId);
            return resourceCategoryMapper.updateByPrimaryKey(resourceCategory);
        }
        return 0;
    }
}
