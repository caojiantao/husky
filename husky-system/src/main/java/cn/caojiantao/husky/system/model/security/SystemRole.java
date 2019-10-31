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
public class SystemRole extends BaseModel {

    private String name;
}
