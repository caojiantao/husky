package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.RoleMenu;
import cn.caojiantao.common.base.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu, Object> {

    List<Integer> listMenuIdsByRoleId(int roleId);

    void saveRoleMenus(List<RoleMenu> roleMenus);

    int deleteByRoleId(int roleId);

    void deleteByMenuId(int menuId);
}
