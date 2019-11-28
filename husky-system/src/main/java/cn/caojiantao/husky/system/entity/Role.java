package cn.caojiantao.husky.system.entity;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@TableName("system_role")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

    private String name;
}
