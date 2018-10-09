package cn.caojiantao.system.model.dictionary;


import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictSet extends BaseModel{

    private Integer parentValueId;
    private String code;
    private String name;
    private String desc;
}
