package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.Menu;
import cn.caojiantao.system.query.MenuQuery;
import cn.caojiantao.common.base.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface MenuMapper extends BaseMapper<Menu, MenuQuery> {

    /**
     * 根据角色列表，获取菜单集合（去重）
     *
     * @param roleIds 角色ID结合
     * @return 菜单集合
     */
    List<Menu> getMenusByRoleIds(List<Integer> roleIds);
}
