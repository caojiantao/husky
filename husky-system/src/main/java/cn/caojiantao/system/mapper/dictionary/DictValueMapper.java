package cn.caojiantao.system.mapper.dictionary;

import cn.caojiantao.system.model.dictionary.DictValue;
import cn.caojiantao.system.query.DictionaryQuery;
import cn.caojiantao.common.base.BaseMapper;

/**
 * @author caojiantao
 */
public interface DictValueMapper extends BaseMapper<DictValue, DictionaryQuery> {

    int deleteDictValueBySetId(int setId);
}
