package cn.caojiantao.husky.system.mapper.security;

import cn.caojiantao.husky.system.model.security.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByUserId(int userId);

    List<Menu> getMenusByRoleId(int roleId);
}
