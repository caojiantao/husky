package cn.caojiantao.husky.resource.query;

import cn.caojiantao.husky.resource.entity.People;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PeopleQuery extends Page<People> {

    private String name;
}
