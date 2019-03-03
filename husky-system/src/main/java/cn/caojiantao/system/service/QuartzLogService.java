package cn.caojiantao.system.service;

import cn.caojiantao.system.mapper.quartz.QuartzLogMapper;
import cn.caojiantao.system.model.quartz.QuartzLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author caojiantao
 */
@Service
public class QuartzLogService extends ServiceImpl<QuartzLogMapper, QuartzLog> {
}
