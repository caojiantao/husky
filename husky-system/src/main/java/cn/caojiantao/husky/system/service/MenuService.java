package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.LoginContext;
import cn.caojiantao.husky.system.dto.MenuDTO;
import cn.caojiantao.husky.system.entity.Menu;
import cn.caojiantao.husky.system.mapper.MenuMapper;
import cn.caojiantao.husky.system.mapper.RoleMenuMapper;
import cn.caojiantao.husky.system.entity.RoleMenu;
import cn.caojiantao.husky.system.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
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

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByMenuId(int id) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(id);
        Wrapper<RoleMenu> wrapper = Wrappers.query(roleMenu);
        roleMenuMapper.delete(wrapper);
        return menuMapper.deleteById(id) > 0;
    }

    public List<MenuDTO> getPersonalMenuListAsTree() {
        User user = LoginContext.getUser();
        List<Menu> menuList = menuMapper.getMenuListByUserId(user.getId());
        return formatSystemMenu(menuList);
    }

    public List<MenuDTO> getMenuListAsTree() {
        // 顺序查询所有菜单
        QueryWrapper<Menu> wrapper = Wrappers.query();
        wrapper.orderByDesc("weight");
        List<Menu> menuList = list(wrapper);
        return formatSystemMenu(menuList);
    }

    private List<MenuDTO> formatSystemMenu(List<Menu> menuList) {
        LinkedMultiValueMap<Integer, Menu> menuMultiValueMap = new LinkedMultiValueMap<>();
        menuList.forEach(menu -> menuMultiValueMap.add(menu.getParentId(), menu));
        MenuDTO root = new MenuDTO();
        root.setId(0);
        initChildren(root, menuMultiValueMap);
        return root.getChildren();
    }

    private void initChildren(MenuDTO menuDTO, LinkedMultiValueMap<Integer, Menu> menuLinkedMultiValueMap) {
        List<Menu> children = menuLinkedMultiValueMap.get(menuDTO.getId());
        if (!CollectionUtils.isEmpty(children)) {
            if (menuDTO.getChildren() == null) {
                menuDTO.setChildren(new ArrayList<>());
            }
            children.forEach(child -> {
                MenuDTO childDTO = new MenuDTO();
                BeanUtils.copyProperties(child, childDTO);
                initChildren(childDTO, menuLinkedMultiValueMap);
                menuDTO.getChildren().add(childDTO);
            });
        }
    }
}
