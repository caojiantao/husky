package cn.caojiantao.husky.system.mapper.security;

import cn.caojiantao.husky.system.model.security.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户实体数据持久层
 *
 * @author caojiantao
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    SystemUser getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
