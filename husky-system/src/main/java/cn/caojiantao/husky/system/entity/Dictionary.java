package cn.caojiantao.husky.system.entity;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@TableName("system_dictionary")
@EqualsAndHashCode(callSuper = false)
public class Dictionary extends BaseEntity {

    private Integer parentId;
    private String code;
    private String name;
    private String value;
    private String description;
    @TableField("`order`")
    private Integer order;
}
