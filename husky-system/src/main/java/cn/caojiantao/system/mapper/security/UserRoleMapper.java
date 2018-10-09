package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.UserRole;
import cn.caojiantao.common.base.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface UserRoleMapper extends BaseMapper<UserRole, Object> {

    List<Integer> listRoleIdByUserId(int userId);

    void saveUserRoles(List<UserRole> userRoles);

    int deleteByUserId(long userId);
}
