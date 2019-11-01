package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.LoginContext;
import cn.caojiantao.husky.system.dto.security.SystemMenuDTO;
import cn.caojiantao.husky.system.mapper.security.SystemMenuMapper;
import cn.caojiantao.husky.system.mapper.security.SystemRoleMenuMapper;
import cn.caojiantao.husky.system.model.security.SystemMenu;
import cn.caojiantao.husky.system.model.security.SystemRoleMenu;
import cn.caojiantao.husky.system.model.security.SystemUser;
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
public class SystemMenuService extends ServiceImpl<SystemMenuMapper, SystemMenu> {

    private final SystemMenuMapper systemMenuMapper;
    private final SystemRoleMenuMapper systemRoleMenuMapper;

    @Autowired
    public SystemMenuService(SystemMenuMapper systemMenuMapper, SystemRoleMenuMapper systemRoleMenuMapper) {
        this.systemMenuMapper = systemMenuMapper;
        this.systemRoleMenuMapper = systemRoleMenuMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByMenuId(int id) {
        SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
        systemRoleMenu.setMenuId(id);
        Wrapper<SystemRoleMenu> wrapper = Wrappers.query(systemRoleMenu);
        systemRoleMenuMapper.delete(wrapper);
        return systemMenuMapper.deleteById(id) > 0;
    }

    public List<SystemMenuDTO> getPersonalMenuListAsTree() {
        SystemUser user = LoginContext.getUser();
        List<SystemMenu> systemMenuList = systemMenuMapper.getMenuListByUserId(user.getId());
        return formatSystemMenu(systemMenuList);
    }

    public List<SystemMenuDTO> getMenuListAsTree() {
        // 顺序查询所有菜单
        QueryWrapper<SystemMenu> wrapper = Wrappers.query();
        wrapper.orderByDesc("weight");
        List<SystemMenu> systemMenuList = list(wrapper);
        return formatSystemMenu(systemMenuList);
    }

    private List<SystemMenuDTO> formatSystemMenu(List<SystemMenu> menuList) {
        LinkedMultiValueMap<Integer, SystemMenu> menuMultiValueMap = new LinkedMultiValueMap<>();
        menuList.forEach(menu -> menuMultiValueMap.add(menu.getParentId(), menu));
        SystemMenuDTO root = new SystemMenuDTO();
        root.setId(0);
        initChildren(root, menuMultiValueMap);
        return root.getChildren();
    }

    private void initChildren(SystemMenuDTO menuDTO, LinkedMultiValueMap<Integer, SystemMenu> menuLinkedMultiValueMap) {
        List<SystemMenu> children = menuLinkedMultiValueMap.get(menuDTO.getId());
        if (!CollectionUtils.isEmpty(children)) {
            if (menuDTO.getChildren() == null) {
                menuDTO.setChildren(new ArrayList<>());
            }
            children.forEach(child -> {
                SystemMenuDTO childDTO = new SystemMenuDTO();
                BeanUtils.copyProperties(child, childDTO);
                initChildren(childDTO, menuLinkedMultiValueMap);
                menuDTO.getChildren().add(childDTO);
            });
        }
    }
}
