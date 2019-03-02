package cn.caojiantao.system.service.impl;

import cn.caojiantao.system.mapper.security.MenuMapper;
import cn.caojiantao.system.mapper.security.RoleMenuMapper;
import cn.caojiantao.system.model.security.Menu;
import cn.caojiantao.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class MenuServiceImpl implements IMenuService {

    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public List<Menu> getMenusByUserId(int userId) {
        return menuMapper.getMenusByUserId(userId);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    @Override
    public boolean saveMenu(Menu menu) {
        if (menu.getId() == null || menu.getId() == 0) {
            menu.setGmtCreate(LocalDateTime.now());
            menuMapper.insert(menu);
            return menu.getId() > 0;
        } else {
            menu.setGmtModified(LocalDateTime.now());
            return menuMapper.updateById(menu) > 0;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenuById(int id) {
        roleMenuMapper.deleteByMenuId(id);
        return menuMapper.deleteById(id) > 0;
    }

    @Override
    public Menu getMenuById(int id) {
        return menuMapper.getById(id);
    }
}
