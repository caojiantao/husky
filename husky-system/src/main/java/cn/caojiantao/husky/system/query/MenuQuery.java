package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.entity.Menu;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuQuery extends Page<Menu> {
}
