package cn.caojiantao.system.model.security;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体类
 *
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu extends BaseModel {

    private String name;
    private Integer parentId;
    private String href;
    private String iconClass;
    private String componentPath;
    private Integer order;
}
