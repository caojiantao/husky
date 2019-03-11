package cn.caojiantao.husky.system.model.security;

import cn.caojiantao.husky.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@TableName("system_role")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseModel {

    private String name;
}
