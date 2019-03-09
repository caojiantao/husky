package cn.caojiantao.huksy.system.service;

import cn.caojiantao.huksy.system.mapper.dictionary.DictionaryMapper;
import cn.caojiantao.huksy.system.dto.DictionaryDTO;
import cn.caojiantao.huksy.system.model.dictionary.Dictionary;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caojiantao
 */
@Service
public class DictionaryService extends ServiceImpl<DictionaryMapper, Dictionary> {

    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryService(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }

    public DictionaryDTO getDictionaryById(int id) {
        return dictionaryMapper.getDictionaryDTOById(id);
    }
}
