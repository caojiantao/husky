package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.model.security.SystemMenu;
import cn.caojiantao.husky.system.model.security.SystemRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemRoleDTO extends SystemRole {

    private List<SystemMenu> systemMenus;
}
