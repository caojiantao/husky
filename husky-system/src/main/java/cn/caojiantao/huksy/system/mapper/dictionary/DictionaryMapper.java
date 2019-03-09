package cn.caojiantao.huksy.system.mapper.dictionary;

import cn.caojiantao.huksy.system.dto.DictionaryDTO;
import cn.caojiantao.huksy.system.model.dictionary.Dictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author caojiantao
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    DictionaryDTO getDictionaryDTOById(int id);
}
