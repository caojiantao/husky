package cn.caojiantao.system.service.impl;

import cn.caojiantao.system.dto.RoleDTO;
import cn.caojiantao.system.mapper.security.MenuMapper;
import cn.caojiantao.system.mapper.security.RoleMapper;
import cn.caojiantao.system.mapper.security.RoleMenuMapper;
import cn.caojiantao.system.model.security.Menu;
import cn.caojiantao.system.model.security.Role;
import cn.caojiantao.system.model.security.RoleMenu;
import cn.caojiantao.system.query.RoleQuery;
import cn.caojiantao.system.service.IRoleService;
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
public class RoleServiceImpl implements IRoleService {

    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public List<Role> getRoles(RoleQuery query) {
        return roleMapper.getList(query);
    }

    @Override
    public int countRoles(RoleQuery query) {
        return roleMapper.countList(query);
    }

    @Override
    public RoleDTO getRoleWithMenusById(int id) {
        Role role = roleMapper.getById(id);
        if (role == null) {
            return null;
        } else {
            RoleDTO dto = new RoleDTO();
            BeanUtils.copyProperties(role, dto);
            List<Menu> menus = menuMapper.getMenusByRoleId(id);
            dto.setMenus(menus);
            return dto;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setGmtCreate(LocalDateTime.now());
        roleMapper.insert(role);
        initRoleMenu(roleDTO);
        return role.getId() > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setGmtModified(LocalDateTime.now());
        initRoleMenu(roleDTO);
        return roleMapper.updateById(role) > 0;
    }

    @Override
    public boolean deleteRoleById(int id) {
        roleMenuMapper.deleteByRoleId(id);
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
            roleMenuMapper.deleteByRoleId(roleId);
            List<Menu> menus = roleDTO.getMenus();
            if (!CollectionUtils.isEmpty(menus)) {
                List<RoleMenu> roleMenus = menus.stream().map(menu -> new RoleMenu(roleId, menu.getId())).collect(Collectors.toList());
                roleMenuMapper.saveRoleMenus(roleMenus);
            }
        }
    }
}
