package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.entity.Dictionary;
import cn.caojiantao.husky.system.mapper.DictionaryMapper;
import cn.caojiantao.husky.system.dto.DictionaryDTO;
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
