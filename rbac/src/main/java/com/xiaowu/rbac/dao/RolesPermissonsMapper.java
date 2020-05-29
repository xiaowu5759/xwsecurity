package com.xiaowu.rbac.dao;

import com.xiaowu.rbac.entity.RolesPermissons;
import com.xiaowu.rbac.entity.RolesPermissonsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolesPermissonsMapper {
    long countByExample(RolesPermissonsExample example);

    int deleteByExample(RolesPermissonsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolesPermissons record);

    int insertSelective(RolesPermissons record);

    List<RolesPermissons> selectByExample(RolesPermissonsExample example);

    RolesPermissons selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolesPermissons record, @Param("example") RolesPermissonsExample example);

    int updateByExample(@Param("record") RolesPermissons record, @Param("example") RolesPermissonsExample example);

    int updateByPrimaryKeySelective(RolesPermissons record);

    int updateByPrimaryKey(RolesPermissons record);
}