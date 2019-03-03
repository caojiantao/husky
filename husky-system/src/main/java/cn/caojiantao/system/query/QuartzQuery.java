package cn.caojiantao.system.query;

import cn.caojiantao.system.model.quartz.Quartz;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 * @since 2018-02-13 21:40:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuartzQuery extends Page<Quartz> {
}
