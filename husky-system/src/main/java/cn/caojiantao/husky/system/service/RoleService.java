package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.dto.RoleDTO;
import cn.caojiantao.husky.system.entity.Menu;
import cn.caojiantao.husky.system.entity.Role;
import cn.caojiantao.husky.system.mapper.MenuMapper;
import cn.caojiantao.husky.system.mapper.RoleMapper;
import cn.caojiantao.husky.system.entity.RoleMenu;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caojiantao
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuService roleMenuService;

    @Autowired
    public RoleService(RoleMapper roleMapper, MenuMapper menuMapper, RoleMenuService roleMenuService) {
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.roleMenuService = roleMenuService;
    }

    public RoleDTO getRoleWithMenusById(int id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            return null;
        } else {
            RoleDTO dto = new RoleDTO();
            BeanUtils.copyProperties(role, dto);
            List<Menu> menus = menuMapper.getMenuListByRoleId(id);
            dto.setMenus(menus);
            return dto;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setGmtCreate(LocalDateTime.now());
        roleMapper.insert(role);
        initRoleMenu(roleDTO);
        return role.getId() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setGmtModified(LocalDateTime.now());
        initRoleMenu(roleDTO);
        return roleMapper.updateById(role) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleById(int id) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(id);
        Wrapper<RoleMenu> wrapper = Wrappers.query(roleMenu);
        roleMenuService.remove(wrapper);
        return roleMapper.deleteById(id) > 0;
    }

    /**
     * 初始化"角色-菜单"映射
     *
     * @param roleDTO 角色数据实体
     */
    private void initRoleMenu(RoleDTO roleDTO) {
        Integer roleId = roleDTO.getId();
        if (roleId != null && roleId > 0) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            Wrapper<RoleMenu> wrapper = Wrappers.query(roleMenu);
            roleMenuService.remove(wrapper);
            List<Menu> menus = roleDTO.getMenus();
            if (!CollectionUtils.isEmpty(menus)) {
                List<RoleMenu> roleMenus = menus.stream().map(menu -> new RoleMenu(roleId, menu.getId())).collect(Collectors.toList());
                roleMenuService.saveBatch(roleMenus);
            }
        }
    }
}
