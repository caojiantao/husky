package cn.caojiantao.system.service.impl;

import cn.caojiantao.system.dto.DictionaryDTO;
import cn.caojiantao.system.mapper.dictionary.DictionaryMapper;
import cn.caojiantao.system.model.dictionary.Dictionary;
import cn.caojiantao.system.query.DictionaryQuery;
import cn.caojiantao.system.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
public class DictionaryServiceImpl implements IDictionaryService {

    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    public DictionaryDTO getDictionaryById(int id) {
        return dictionaryMapper.getDictionaryDTOById(id);
    }

    @Override
    public List<Dictionary> getDictionaries(DictionaryQuery query) {
        return dictionaryMapper.getObjects(query);
    }

    @Override
    public int countDictionaries(DictionaryQuery query) {
        return dictionaryMapper.countObjects(query);
    }

    @Override
    public boolean addDictionary(Dictionary dictionary) {
        dictionary.setGmtCreate(LocalDateTime.now());
        dictionaryMapper.insert(dictionary);
        return dictionary.getId() > 0;
    }

    @Override 
    public boolean updateDictionary(Dictionary dictionary) {
        dictionary.setGmtModified(LocalDateTime.now());
        return dictionaryMapper.updateById(dictionary) > 0;
    }

    @Override
    public boolean deleteDictionaryById(int id) {
        return dictionaryMapper.deleteById(id) > 0;
    }
}
