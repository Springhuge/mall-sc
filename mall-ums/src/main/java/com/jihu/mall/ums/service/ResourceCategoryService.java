package com.jihu.mall.ums.service;

import com.jihu.mall.ums.example.UmsResourceCategoryExample;
import com.jihu.mall.ums.example.UmsResourceExample;
import com.jihu.mall.ums.mapper.UmsResourceCategoryMapper;
import com.jihu.mall.ums.model.UmsResourceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resourceCategoryService")
public class ResourceCategoryService {

    @Autowired
    private UmsResourceCategoryMapper resourceCategoryMapper;

    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(example);
    }
}
