package cn.caojiantao.husky.resource.query;

import cn.caojiantao.husky.resource.entity.Audio;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AudioQuery extends Page<Audio> {

    private String name;
}
