package cn.caojiantao.system.dto;

import cn.caojiantao.system.model.dictionary.Dictionary;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DictionaryDTO extends Dictionary {

    /**
     * 父级字典名称
     */
    private String parentName;
}
