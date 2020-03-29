package com.jihu.mall.ums.service;

import com.jihu.mall.ums.example.UmsResourceExample;
import com.jihu.mall.ums.mapper.UmsResourceMapper;
import com.jihu.mall.ums.model.UmsResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resourceService")
public class ResourceService {

    @Autowired
    private UmsResourceMapper resourceMapper;

    public List<UmsResource> listAll() {
        return resourceMapper.selectByExample(new UmsResourceExample());
    }
}
