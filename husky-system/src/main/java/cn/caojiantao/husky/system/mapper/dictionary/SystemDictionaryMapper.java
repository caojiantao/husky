package cn.caojiantao.husky.system.mapper.dictionary;

import cn.caojiantao.husky.system.dto.SystemDictionaryDTO;
import cn.caojiantao.husky.system.model.dictionary.SystemDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author caojiantao
 */
public interface SystemDictionaryMapper extends BaseMapper<SystemDictionary> {

    SystemDictionaryDTO getDictionaryDTOById(int id);
}
