package cn.caojiantao.system.model.dictionary;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Dictionary extends BaseModel {

    private Integer parentId;
    private String code;
    private String name;
    private String value;
    private String description;
    private Integer order;
}
