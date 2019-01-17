package cn.caojiantao.system.service.impl;

import cn.caojiantao.system.QuartzJobManager;
import cn.caojiantao.system.mapper.quartz.QuartzMapper;
import cn.caojiantao.system.model.quartz.Quartz;
import cn.caojiantao.system.service.IQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuartzServiceImpl implements IQuartzService, InitializingBean {

    private final QuartzMapper quartzMapper;
    private final QuartzJobManager manager;

    @Autowired
    public QuartzServiceImpl(QuartzMapper quartzMapper, QuartzJobManager manager) {
        this.quartzMapper = quartzMapper;
        this.manager = manager;
    }

    @Override
    public Quartz getQuartzById(int id) {
        return quartzMapper.getById(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("====================【开始初始化定时任务】====================");
        List<Quartz> tasks = quartzMapper.getObjects(null);
        for (Quartz task : tasks) {
            manager.addJob(task);
        }
    }
}
