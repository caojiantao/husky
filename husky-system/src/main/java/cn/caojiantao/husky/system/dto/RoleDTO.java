package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.model.security.Menu;
import cn.caojiantao.husky.system.model.security.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends Role {

    private List<Menu> menus;
}
