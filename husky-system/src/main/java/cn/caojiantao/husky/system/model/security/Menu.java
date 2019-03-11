package cn.caojiantao.husky.system.model.security;

import cn.caojiantao.husky.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体类
 *
 * @author caojiantao
 */
@Data
@TableName("system_menu")
@EqualsAndHashCode(callSuper = false)
public class Menu extends BaseModel {

    private String name;
    private Integer parentId;
    private String href;
    private String iconClass;
    private String componentPath;
    @TableField("`order`")
    private Integer order;
}
