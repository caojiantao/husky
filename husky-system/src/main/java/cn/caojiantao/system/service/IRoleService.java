package cn.caojiantao.system.service;

import cn.caojiantao.system.dto.RoleDTO;
import cn.caojiantao.system.model.security.Role;
import cn.caojiantao.system.query.RoleQuery;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IRoleService {

    List<Role> getRoles(RoleQuery query);

    int countRoles(RoleQuery query);

    RoleDTO getRoleWithMenusById(int id);

    boolean addRole(RoleDTO roleDTO);

    boolean updateRole(RoleDTO roleDTO);

    boolean deleteRoleById(int id);
}
