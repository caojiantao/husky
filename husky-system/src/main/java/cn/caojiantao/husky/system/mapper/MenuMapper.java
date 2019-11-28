package cn.caojiantao.husky.system.mapper;

import cn.caojiantao.husky.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author caojiantao
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuListByUserId(int userId);

    List<Menu> getMenuListByRoleId(int roleId);
}
