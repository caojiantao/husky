package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.model.quartz.SystemQuartzLog;
import cn.caojiantao.husky.system.mapper.quartz.SystemQuartzLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author caojiantao
 */
@Service
public class SystemQuartzLogService extends ServiceImpl<SystemQuartzLogMapper, SystemQuartzLog> {
}
