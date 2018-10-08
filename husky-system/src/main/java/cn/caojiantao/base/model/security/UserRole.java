package cn.caojiantao.base.model.security;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseModel{

    private Integer userId;
    private Integer roleId;
}
