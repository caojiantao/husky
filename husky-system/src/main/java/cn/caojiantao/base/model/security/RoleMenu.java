package cn.caojiantao.base.model.security;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleMenu extends BaseModel{

    private Integer roleId;
    private Integer menuId;
}
