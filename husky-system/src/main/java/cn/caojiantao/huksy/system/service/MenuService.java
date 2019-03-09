package cn.caojiantao.huksy.system.service;

import cn.caojiantao.huksy.system.model.security.Menu;
import cn.caojiantao.huksy.system.mapper.security.MenuMapper;
import cn.caojiantao.huksy.system.mapper.security.RoleMenuMapper;
import cn.caojiantao.huksy.system.model.security.RoleMenu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuService(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    public List<Menu> getMenusByUserId(int userId) {
        return menuMapper.getMenusByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(int id) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(id);
        Wrapper<RoleMenu> wrapper = Wrappers.query(roleMenu);
        roleMenuMapper.delete(wrapper);
        return menuMapper.deleteById(id) > 0;
    }
}
