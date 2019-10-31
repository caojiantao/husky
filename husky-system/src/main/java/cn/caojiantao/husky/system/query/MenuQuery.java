package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.model.security.SystemMenu;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuQuery extends Page<SystemMenu> {
}