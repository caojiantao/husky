package cn.caojiantao.huksy.system.query;

import cn.caojiantao.huksy.system.model.security.Menu;
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
