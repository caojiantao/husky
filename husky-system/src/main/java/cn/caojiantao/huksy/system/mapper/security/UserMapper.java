package cn.caojiantao.huksy.system.mapper.security;

import cn.caojiantao.huksy.system.model.security.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户实体数据持久层
 *
 * @author caojiantao
 */
public interface UserMapper extends BaseMapper<User> {

    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
