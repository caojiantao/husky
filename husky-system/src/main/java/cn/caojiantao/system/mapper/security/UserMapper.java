package cn.caojiantao.system.mapper.security;

import cn.caojiantao.system.model.security.User;
import cn.caojiantao.system.query.UserQuery;
import cn.caojiantao.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户实体数据持久层
 *
 * @author caojiantao
 */
public interface UserMapper extends BaseMapper<User, UserQuery> {

    User login(@Param("username") String username, @Param("password") String password);
}
