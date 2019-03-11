package cn.caojiantao.husky.system.model.dictionary;

import cn.caojiantao.husky.common.base.BaseModel;
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
public class Dictionary extends BaseModel {

    private Integer parentId;
    private String code;
    private String name;
    private String value;
    private String description;
    @TableField("`order`")
    private Integer order;
}
