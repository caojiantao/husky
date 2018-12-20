package cn.caojiantao.system.model.security;

import cn.caojiantao.common.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleMenu extends BaseModel{

    private Integer roleId;
    private Integer menuId;
}
