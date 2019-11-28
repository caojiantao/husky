package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.entity.dictionary.SystemDictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemDictionaryDTO extends SystemDictionary {

    /**
     * 父级字典名称
     */
    private String parentName;
}
