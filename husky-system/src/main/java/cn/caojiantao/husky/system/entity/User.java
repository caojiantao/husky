package cn.caojiantao.husky.system.entity;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户信息实体类
 *
 * @author caojiantao
 */
@Data
@TableName("system_user")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private String nickname;
}
