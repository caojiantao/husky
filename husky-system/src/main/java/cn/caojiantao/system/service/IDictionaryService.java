package cn.caojiantao.system.service;

import cn.caojiantao.system.dto.DictionaryDTO;
import cn.caojiantao.system.model.dictionary.Dictionary;
import cn.caojiantao.system.query.DictionaryQuery;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IDictionaryService {

    DictionaryDTO getDictionaryById(int id);

    List<Dictionary> getDictionaries(DictionaryQuery query);

    int countDictionaries(DictionaryQuery query);

    boolean addDictionary(Dictionary dictionary);

    boolean updateDictionary(Dictionary dictionary);

    boolean deleteDictionaryById(int id);
}
