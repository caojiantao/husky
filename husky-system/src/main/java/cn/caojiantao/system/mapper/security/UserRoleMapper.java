package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<Integer> getRoleIdsByUserId(int userId);

    void addUserRoles(List<UserRole> userRoles);

    void deleteByUserId(int userId);
}
