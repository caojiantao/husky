package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.model.security.SystemUser;
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
public class UserQuery extends Page<SystemUser> {

    private Integer id;
    private String username;
    private String password;
}
