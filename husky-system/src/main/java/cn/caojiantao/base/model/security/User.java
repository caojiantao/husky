package cn.caojiantao.base.model.security;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息实体类
 *
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseModel {

    private String username;
    private String password;
    private String nickname;
}
