package cn.caojiantao.system.mapper.dictionary;

import cn.caojiantao.common.base.BaseMapper;
import cn.caojiantao.system.dto.DictionaryDTO;
import cn.caojiantao.system.model.dictionary.Dictionary;
import cn.caojiantao.system.query.DictionaryQuery;

public interface DictionaryMapper extends BaseMapper<Dictionary, DictionaryQuery> {

    DictionaryDTO getDictionaryDTOById(int id);
}
