package cn.caojiantao.huksy.system.service;

import cn.caojiantao.huksy.system.QuartzJobManager;
import cn.caojiantao.huksy.system.mapper.quartz.QuartzMapper;
import cn.caojiantao.huksy.system.model.quartz.Quartz;
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
public class QuartzService extends ServiceImpl<QuartzMapper, Quartz> implements InitializingBean {

    private final QuartzJobManager manager;

    @Autowired
    public QuartzService(QuartzJobManager manager) {
        this.manager = manager;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean changeStatus(Quartz quartz) {
        quartz.setGmtModified(LocalDateTime.now());
        if (updateById(quartz)) {
            quartz = getById(quartz.getId());
            boolean status = quartz.getStatus();
            // 根据状态设定Scheduler
            if (status) {
                manager.addJob(quartz);
            } else {
                manager.pauseJob(quartz);
            }
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("====================【开始初始化定时任务】====================");
        List<Quartz> tasks = list();
        for (Quartz task : tasks) {
            manager.addJob(task);
        }
    }
}
