package cn.caojiantao.system.job;

import cn.caojiantao.common.base.RedisLock;
import cn.caojiantao.system.QuartzJobManager;
import cn.caojiantao.system.model.quartz.Quartz;
import cn.caojiantao.system.service.IQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式定时任务单次执行，
 *
 * @author caojiantao
 */
@Slf4j
public abstract class BaseJob implements Job {

    @Autowired
    private IQuartzService quartzService;
    @Autowired
    private QuartzJobManager manager;
    @Autowired
    private RedisLock lock;

    @Override
    public void execute(JobExecutionContext context) {
        int id = context.getJobDetail().getJobDataMap().getIntValue("id");
        Quartz Quartz = quartzService.getQuartzById(id);
        if (Quartz == null) {
            log.error(context.getJobDetail().getJobClass() + "已被直接删除");
            manager.removeQuartz(context.getTrigger().getKey());
        } else {
            try {
                if ((new CronExpression(Quartz.getCronExpression())).isSatisfiedBy(new Date())) {
                    // 表达式与当前匹配
                    executeUniqueQuartz(context);
                } else {
                    log.error("表达式[" + Quartz.getCronExpression() + "]与当前时间不匹配");
                    manager.removeQuartz(context.getTrigger().getKey());
                    manager.addJob(Quartz);
                }
            } catch (ParseException e) {
                log.error("表达式[" + Quartz.getCronExpression() + "]解析错误");
                manager.removeQuartz(context.getTrigger().getKey());
            }
        }
    }

    /**
     * 分布式锁保证任务单一执行
     */
    private void executeUniqueQuartz(JobExecutionContext context) {
        String jobClass = context.getJobDetail().getJobClass().getName();
        String requestId = UUID.randomUUID().toString();
        try {
            // 设置过期时间为1小时
            if (lock.tryLock(jobClass, requestId, 1, TimeUnit.HOURS)) {
                log.info(jobClass + "开始执行...");
                // 实际quartz执行逻辑
                doExecute();
            } else {
                log.info(jobClass + "获取执行锁失败");
            }
        } catch (Throwable e) {
            log.error("执行定时任务出现错误：", e);
        } finally {
            // 保证会释放锁
            if (!lock.releaseLock(jobClass, requestId)) {
                log.info(jobClass + "释放执行锁失败");
            }
        }
    }

    /**
     * 定时任务实际业务逻辑
     */
    public abstract void doExecute();
}
