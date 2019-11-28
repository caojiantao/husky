package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.entity.Dictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictionaryDTO extends Dictionary {

    /**
     * 父级字典名称
     */
    private String parentName;
}
