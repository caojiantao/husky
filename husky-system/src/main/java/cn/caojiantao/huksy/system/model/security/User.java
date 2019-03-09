package cn.caojiantao.huksy.system.model.security;

import cn.caojiantao.husky.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息实体类
 *
 * @author caojiantao
 */
@Data
@TableName("system_user")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseModel {

    private String username;
    private String password;
    private String nickname;
}
