package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.dto.SystemRoleDTO;
import cn.caojiantao.husky.system.mapper.security.SystemMenuMapper;
import cn.caojiantao.husky.system.mapper.security.SystemRoleMapper;
import cn.caojiantao.husky.system.entity.security.SystemMenu;
import cn.caojiantao.husky.system.entity.security.SystemRole;
import cn.caojiantao.husky.system.entity.security.SystemRoleMenu;
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
public class SystemRoleService extends ServiceImpl<SystemRoleMapper, SystemRole> {

    private final SystemRoleMapper systemRoleMapper;
    private final SystemMenuMapper systemMenuMapper;
    private final SystemRoleMenuService systemRoleMenuService;

    @Autowired
    public SystemRoleService(SystemRoleMapper systemRoleMapper, SystemMenuMapper systemMenuMapper, SystemRoleMenuService systemRoleMenuService) {
        this.systemRoleMapper = systemRoleMapper;
        this.systemMenuMapper = systemMenuMapper;
        this.systemRoleMenuService = systemRoleMenuService;
    }

    public SystemRoleDTO getRoleWithMenusById(int id) {
        SystemRole systemRole = systemRoleMapper.selectById(id);
        if (systemRole == null) {
            return null;
        } else {
            SystemRoleDTO dto = new SystemRoleDTO();
            BeanUtils.copyProperties(systemRole, dto);
            List<SystemMenu> systemMenus = systemMenuMapper.getMenuListByRoleId(id);
            dto.setSystemMenus(systemMenus);
            return dto;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(SystemRoleDTO roleDTO) {
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(roleDTO, systemRole);
        systemRole.setGmtCreate(LocalDateTime.now());
        systemRoleMapper.insert(systemRole);
        initRoleMenu(roleDTO);
        return systemRole.getId() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(SystemRoleDTO roleDTO) {
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(roleDTO, systemRole);
        systemRole.setGmtModified(LocalDateTime.now());
        initRoleMenu(roleDTO);
        return systemRoleMapper.updateById(systemRole) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleById(int id) {
        SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
        systemRoleMenu.setRoleId(id);
        Wrapper<SystemRoleMenu> wrapper = Wrappers.query(systemRoleMenu);
        systemRoleMenuService.remove(wrapper);
        return systemRoleMapper.deleteById(id) > 0;
    }

    /**
     * 初始化"角色-菜单"映射
     *
     * @param roleDTO 角色数据实体
     */
    private void initRoleMenu(SystemRoleDTO roleDTO) {
        Integer roleId = roleDTO.getId();
        if (roleId != null && roleId > 0) {
            SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
            systemRoleMenu.setRoleId(roleId);
            Wrapper<SystemRoleMenu> wrapper = Wrappers.query(systemRoleMenu);
            systemRoleMenuService.remove(wrapper);
            List<SystemMenu> systemMenus = roleDTO.getSystemMenus();
            if (!CollectionUtils.isEmpty(systemMenus)) {
                List<SystemRoleMenu> systemRoleMenus = systemMenus.stream().map(menu -> new SystemRoleMenu(roleId, menu.getId())).collect(Collectors.toList());
                systemRoleMenuService.saveBatch(systemRoleMenus);
            }
        }
    }
}
