package cn.caojiantao.system.model.security;

import cn.caojiantao.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caojiantao
 */
@Data
@TableName("system_user_role")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserRole extends BaseModel{

    private Integer userId;
    private Integer roleId;
}
