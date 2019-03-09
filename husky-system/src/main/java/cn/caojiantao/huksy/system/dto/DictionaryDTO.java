package cn.caojiantao.huksy.system.dto;

import cn.caojiantao.huksy.system.model.dictionary.Dictionary;
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
