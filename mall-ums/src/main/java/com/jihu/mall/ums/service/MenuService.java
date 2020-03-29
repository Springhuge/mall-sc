package com.jihu.mall.ums.service;

import com.github.pagehelper.PageHelper;
import com.jihu.mall.ums.example.UmsMenuExample;
import com.jihu.mall.ums.mapper.UmsMenuMapper;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.response.UmsMenuNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public List<UmsMenu> list(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        UmsMenuExample example = new UmsMenuExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }

    public UmsMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public int create(UmsMenu menu) {
        menu.setCreateTime(new Date());
        updateLevel(menu);
        return menuMapper.insert(menu);
    }

    private void updateLevel(UmsMenu menu) {
        if(menu.getParentId() == 0){
            //没有父菜单设为一级菜单
            menu.setLevel(0);
        }else{
            //有父菜单，根据父菜单level+1
            UmsMenu parentMenu = menuMapper.selectByPrimaryKey(menu.getParentId());
            if(Optional.ofNullable(parentMenu).isPresent()) {
                menu.setLevel(parentMenu.getLevel() + 1);
            }else{
                menu.setLevel(0);
            }
        }

    }

    public int update(Long menuId, UmsMenu menu) {
        UmsMenu temp = menuMapper.selectByPrimaryKey(menuId);
        if(Optional.ofNullable(temp).isPresent()){
            menu.setId(menuId);
            updateLevel(menu);
            return menuMapper.updateByPrimaryKey(menu);
        }
        return 0;
    }

    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    public int updateHidden(Long id, Integer hidden) {
        UmsMenu temp = menuMapper.selectByPrimaryKey(id);
        if(Optional.ofNullable(temp).isPresent()){
            temp.setHidden(hidden);
            updateLevel(temp);
            return menuMapper.updateByPrimaryKey(temp);
        }
        return 0;
    }
}
