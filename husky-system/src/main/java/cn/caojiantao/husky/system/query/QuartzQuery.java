package cn.caojiantao.husky.system.query;

import cn.caojiantao.husky.system.model.quartz.SystemQuartz;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzQuery extends Page<SystemQuartz> {
}
