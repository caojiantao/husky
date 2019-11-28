package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.entity.Menu;
import cn.caojiantao.husky.system.entity.Role;
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
