package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.mapper.dictionary.SystemDictionaryMapper;
import cn.caojiantao.husky.system.dto.SystemDictionaryDTO;
import cn.caojiantao.husky.system.model.dictionary.SystemDictionary;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author caojiantao
 */
@Service
public class SystemDictionaryService extends ServiceImpl<SystemDictionaryMapper, SystemDictionary> {

    private final SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    public SystemDictionaryService(SystemDictionaryMapper systemDictionaryMapper) {
        this.systemDictionaryMapper = systemDictionaryMapper;
    }

    public SystemDictionaryDTO getDictionaryById(int id) {
        return systemDictionaryMapper.getDictionaryDTOById(id);
    }
}
