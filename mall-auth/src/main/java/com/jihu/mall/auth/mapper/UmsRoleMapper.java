package com.jihu.mall.auth.mapper;

import com.jihu.mall.ums.example.UmsRoleExample;
import com.jihu.mall.ums.model.UmsMenu;
import com.jihu.mall.ums.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsRoleMapper {
    int countByExample(UmsRoleExample example);

    int deleteByExample(UmsRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsRole record);

    int insertSelective(UmsRole record);

    List<UmsRole> selectByExample(UmsRoleExample example);

    UmsRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsRole record, @Param("example") UmsRoleExample example);

    int updateByExample(@Param("record") UmsRole record, @Param("example") UmsRoleExample example);

    int updateByPrimaryKeySelective(UmsRole record);

    int updateByPrimaryKey(UmsRole record);

    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);
}