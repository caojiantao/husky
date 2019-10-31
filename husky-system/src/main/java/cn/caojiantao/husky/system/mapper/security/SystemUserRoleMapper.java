package cn.caojiantao.husky.system.mapper.security;

import cn.caojiantao.husky.system.model.security.SystemUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {

    List<Integer> getRoleIdsByUserId(int userId);

    void addUserRoles(List<SystemUserRole> systemUserRoles);

    void deleteByUserId(int userId);
}
