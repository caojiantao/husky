package cn.caojiantao.huksy.system.service;

import cn.caojiantao.huksy.system.model.quartz.QuartzLog;
import cn.caojiantao.huksy.system.mapper.quartz.QuartzLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author caojiantao
 */
@Service
public class QuartzLogService extends ServiceImpl<QuartzLogMapper, QuartzLog> {
}
