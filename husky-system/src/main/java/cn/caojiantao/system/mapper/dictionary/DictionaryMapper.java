package cn.caojiantao.system.mapper.dictionary;

import cn.caojiantao.system.dto.DictionaryDTO;
import cn.caojiantao.system.model.dictionary.Dictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author caojiantao
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    DictionaryDTO getDictionaryDTOById(int id);
}
