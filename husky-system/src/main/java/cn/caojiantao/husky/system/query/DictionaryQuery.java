package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.entity.Dictionary;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DictionaryQuery extends Page<Dictionary> {

    private String code;
    private String name;
}
