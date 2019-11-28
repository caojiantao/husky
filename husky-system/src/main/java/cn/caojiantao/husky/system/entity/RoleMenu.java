package cn.caojiantao.husky.system.entity;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caojiantao
 */
@Data
@TableName("system_role_menu")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RoleMenu extends BaseEntity {

    private Integer roleId;
    private Integer menuId;
}
