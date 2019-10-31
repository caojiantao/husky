package cn.caojiantao.husky.system.mapper.security;

import cn.caojiantao.husky.system.model.security.SystemMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    List<SystemMenu> getMenusByUserId(int userId);

    List<SystemMenu> getMenusByRoleId(int roleId);
}
