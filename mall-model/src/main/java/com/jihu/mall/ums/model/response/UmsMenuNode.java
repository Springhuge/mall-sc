package com.jihu.mall.ums.model.response;

import com.jihu.mall.ums.model.UmsMenu;
import lombok.Data;

import java.util.List;

@Data
public class UmsMenuNode extends UmsMenu{

    private List<UmsMenuNode> children;
}
