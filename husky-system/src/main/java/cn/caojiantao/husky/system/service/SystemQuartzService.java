package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.QuartzJobManager;
import cn.caojiantao.husky.system.mapper.quartz.SystemQuartzMapper;
import cn.caojiantao.husky.system.entity.quartz.SystemQuartz;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author caojiantao
 */
@Service
@Slf4j
public class SystemQuartzService extends ServiceImpl<SystemQuartzMapper, SystemQuartz> implements InitializingBean {

    private final QuartzJobManager manager;

    @Autowired
    public SystemQuartzService(QuartzJobManager manager) {
        this.manager = manager;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean changeStatus(SystemQuartz systemQuartz) {
        systemQuartz.setGmtModified(LocalDateTime.now());
        if (updateById(systemQuartz)) {
            systemQuartz = getById(systemQuartz.getId());
            boolean status = systemQuartz.getStatus();
            // 根据状态设定Scheduler
            if (status) {
                manager.addJob(systemQuartz);
            } else {
                manager.pauseJob(systemQuartz);
            }
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("====================【开始初始化定时任务】====================");
        List<SystemQuartz> tasks = list();
        for (SystemQuartz task : tasks) {
            manager.addJob(task);
        }
    }
}
