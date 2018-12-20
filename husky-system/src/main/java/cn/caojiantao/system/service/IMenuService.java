package cn.caojiantao.system.service;

import cn.caojiantao.system.model.security.Menu;

import java.util.List;

public interface IMenuService {

    List<Menu> getMenusByUserId(int userId);

    List<Menu> getAllMenus();

    boolean saveMenu(Menu menu);

    boolean deleteMenuById(int id);

    Menu getMenuById(int id);
}
