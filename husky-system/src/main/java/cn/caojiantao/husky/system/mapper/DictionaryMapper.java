package cn.caojiantao.husky.system.mapper;

import cn.caojiantao.husky.system.dto.DictionaryDTO;
import cn.caojiantao.husky.system.entity.Dictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author caojiantao
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    DictionaryDTO getDictionaryDTOById(int id);
}
