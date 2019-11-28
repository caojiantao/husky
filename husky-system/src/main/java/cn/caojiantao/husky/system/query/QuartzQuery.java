package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.entity.Quartz;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzQuery extends Page<Quartz> {
}
