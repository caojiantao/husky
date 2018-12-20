package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.Menu;
import cn.caojiantao.system.query.MenuQuery;
import cn.caojiantao.common.base.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface MenuMapper extends BaseMapper<Menu, MenuQuery> {

    List<Menu> getMenusByUserId(int userId);

    List<Menu> getMenusByRoleId(int roleId);

    List<Menu> getAllMenus();
}
