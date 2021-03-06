package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息DTO
 *
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserQuery extends Page<User> {

    private Integer id;
    private String username;
    private String password;
}
