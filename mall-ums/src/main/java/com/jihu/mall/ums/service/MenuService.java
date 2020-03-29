package com.jihu.mall.ums.service;

import com.jihu.mall.ums.example.UmsMenuExample;
import com.jihu.mall.ums.mapper.UmsMenuMapper;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.response.UmsMenuNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("menuService")
public class MenuService {

    @Autowired
    private UmsMenuMapper menuMapper;

    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menus = menuMapper.selectByExample(new UmsMenuExample());
        List<UmsMenuNode> result = menus.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu,menus)).collect(Collectors.toList());
        return result;
    }

    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu,node);
        List<UmsMenuNode> result = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu,menuList)).collect(Collectors.toList());
        node.setChildren(result);
        return node;
    }
}
