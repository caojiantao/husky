package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.Role;
import cn.caojiantao.system.query.RoleQuery;
import cn.caojiantao.common.base.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface RoleMapper extends BaseMapper<Role, RoleQuery> {

    /**
     * 根据username获取角色集合
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> getRolesByUserId(long userId);
}
