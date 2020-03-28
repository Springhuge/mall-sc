package com.jihu.mall.auth.mapper;

import com.jihu.mall.ums.example.UmsAdminRoleRelationExample;
import com.jihu.mall.ums.model.UmsAdminRoleRelation;
import com.jihu.mall.ums.model.UmsPermission;
import com.jihu.mall.ums.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminRoleRelationMapper {
    int countByExample(UmsAdminRoleRelationExample example);

    int deleteByExample(UmsAdminRoleRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsAdminRoleRelation record);

    int insertSelective(UmsAdminRoleRelation record);

    List<UmsAdminRoleRelation> selectByExample(UmsAdminRoleRelationExample example);

    UmsAdminRoleRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsAdminRoleRelation record, @Param("example") UmsAdminRoleRelationExample example);

    int updateByExample(@Param("record") UmsAdminRoleRelation record, @Param("example") UmsAdminRoleRelationExample example);

    int updateByPrimaryKeySelective(UmsAdminRoleRelation record);

    int updateByPrimaryKey(UmsAdminRoleRelation record);

    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);

    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    int insertList(@Param("list") List<UmsAdminRoleRelation> adminRoleRelationList);
}