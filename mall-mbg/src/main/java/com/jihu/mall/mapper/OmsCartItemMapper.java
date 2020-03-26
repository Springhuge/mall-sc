package com.jihu.mall.mapper;

import com.jihu.mall.model.OmsCartItem;
import com.jihu.mall.model.OmsCartItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsCartItemMapper {
    long countByExample(OmsCartItemExample example);

    int deleteByExample(OmsCartItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsCartItem record);

    int insertSelective(OmsCartItem record);

    List<OmsCartItem> selectByExample(OmsCartItemExample example);

    OmsCartItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsCartItem record, @Param("example") OmsCartItemExample example);

    int updateByExample(@Param("record") OmsCartItem record, @Param("example") OmsCartItemExample example);

    int updateByPrimaryKeySelective(OmsCartItem record);

    int updateByPrimaryKey(OmsCartItem record);
}