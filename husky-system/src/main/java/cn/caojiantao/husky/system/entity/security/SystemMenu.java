package cn.caojiantao.husky.system.entity.security;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 菜单实体类
 *
 * @author caojiantao
 */
@Data
@TableName("system_menu")
@EqualsAndHashCode(callSuper = false)
public class SystemMenu extends BaseEntity {

    @NotEmpty(message = "菜单名称不能为空")
    private String name;
    private Integer parentId;
    private String href;
    private String iconClass;
    private String componentPath;
    private Integer weight;
}
