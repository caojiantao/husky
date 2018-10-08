package cn.caojiantao.base.model.dictionary;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictValue extends BaseModel {

    private Integer setId;
    private String name;
    private String value;
    private Integer order;
    private String desc;
}
