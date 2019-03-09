package cn.caojiantao.huksy.system;

import cn.caojiantao.huksy.system.model.quartz.Quartz;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author caojiantao
 */
@Slf4j
@Component
public class QuartzJobManager {

    private final Scheduler scheduler;

    @Autowired
    public QuartzJobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加定时任务
     */
    @SuppressWarnings("unchecked")
    public void addJob(Quartz job) {
        // 根据name和group获取trigger key，判断是否已经存在该trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                // 新建一个job，并将ID作为携带数据
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                        .withIdentity(job.getName(), job.getGroup())
                        .withDescription(job.getDescription())
                        .usingJobData("id", job.getId())
                        .build();
                // 新建一个trigger
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                        // 定时任务错过处理策略，避免resume时再次执行trigger
                        .withMisfireHandlingInstructionDoNothing();
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // scheduler设置job和trigger
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                        .withMisfireHandlingInstructionDoNothing();
                TriggerBuilder builder = trigger.getTriggerBuilder().withIdentity(triggerKey);
                trigger = builder.withSchedule(scheduleBuilder).build();
                // 根据trigger key重新设置trigger
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            // job状态暂停
            if (!job.getStatus()) {
                pauseJob(job);
            }
        } catch (SchedulerException | ClassNotFoundException e) {
            log.error(job.getJobClass() + "添加报错：", e);
        }
    }

    /**
     * 暂停定时任务
     */
    public void pauseJob(Quartz quartz) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(quartz.getName(), quartz.getGroup()));
        } catch (SchedulerException e) {
            log.error(quartz.getJobClass() + "暂停报错：", e);
        }
    }

    /**
     * 继续定时任务
     */
    public void resumeJob(Quartz quartz) {
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(quartz.getName(), quartz.getGroup()));
        } catch (SchedulerException e) {
            log.error(quartz.getJobClass() + "继续报错：", e);
        }
    }

    /**
     * 移除定时任务
     */
    public void removeQuartz(Quartz quartz) {
        removeQuartz(TriggerKey.triggerKey(quartz.getName(), quartz.getGroup()));
    }

    public void removeQuartz(TriggerKey key) {
        try {
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
        } catch (SchedulerException e) {
            log.error("移除定时任务报错：" + e);
        }
    }

    /**
     * 手动执行定时任务
     */
    public boolean executeJob(Quartz quartz) {
        try {
            scheduler.triggerJob(new JobKey(quartz.getName(), quartz.getGroup()));
            return true;
        } catch (SchedulerException e) {
            log.error(quartz.getJobClass() + "手动执行报错：", e);
            return false;
        }
    }
}
