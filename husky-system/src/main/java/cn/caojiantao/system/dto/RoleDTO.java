package cn.caojiantao.system.dto;

import cn.caojiantao.system.model.security.Menu;
import cn.caojiantao.system.model.security.Role;
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
