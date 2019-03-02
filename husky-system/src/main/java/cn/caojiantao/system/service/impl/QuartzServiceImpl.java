package cn.caojiantao.system.service.impl;

import cn.caojiantao.system.QuartzJobManager;
import cn.caojiantao.system.mapper.quartz.QuartzLogMapper;
import cn.caojiantao.system.mapper.quartz.QuartzMapper;
import cn.caojiantao.system.model.quartz.Quartz;
import cn.caojiantao.system.model.quartz.QuartzLog;
import cn.caojiantao.system.query.QuartzQuery;
import cn.caojiantao.system.service.IQuartzService;
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
public class QuartzServiceImpl implements IQuartzService, InitializingBean {

    private final QuartzMapper quartzMapper;
    private final QuartzJobManager manager;
    private final QuartzLogMapper logMapper;

    @Autowired
    public QuartzServiceImpl(QuartzMapper quartzMapper, QuartzJobManager manager, QuartzLogMapper logMapper) {
        this.quartzMapper = quartzMapper;
        this.manager = manager;
        this.logMapper = logMapper;
    }

    @Override
    public Quartz getQuartzById(int id) {
        return quartzMapper.getById(id);
    }

    @Override
    public int countQuartzs(QuartzQuery query) {
        return quartzMapper.countList(query);
    }

    @Override
    public List<Quartz> getQuartzs(QuartzQuery query) {
        return quartzMapper.getList(query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addQuartz(Quartz quartz) {
        quartz.setStatus(false);
        quartz.setGmtCreate(LocalDateTime.now());
        quartzMapper.insert(quartz);
        return quartz.getId() > 0;
    }

    @Override
    public boolean deleteQuartzById(int id) {
        return quartzMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateQuartz(Quartz quartz) {
        quartz.setGmtModified(LocalDateTime.now());
        return quartzMapper.updateById(quartz) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean changeStatus(Quartz quartz) {
        quartz.setGmtModified(LocalDateTime.now());
        if (quartzMapper.updateStatusById(quartz) > 0) {
            quartz = quartzMapper.getById(quartz.getId());
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
    public void addQuartzLog(QuartzLog log) {
        logMapper.insert(log);
    }

    @Override
    public int countQuartzLog(QuartzQuery query) {
        return logMapper.countList(query);
    }

    @Override
    public List<QuartzLog> getQuartzLog(QuartzQuery query) {
        return logMapper.getList(query);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("====================【开始初始化定时任务】====================");
        List<Quartz> tasks = quartzMapper.getList(null);
        for (Quartz task : tasks) {
            manager.addJob(task);
        }
    }
}
