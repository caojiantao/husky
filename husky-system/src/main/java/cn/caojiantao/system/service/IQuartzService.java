package cn.caojiantao.system.service;

import cn.caojiantao.system.model.quartz.Quartz;
import cn.caojiantao.system.model.quartz.QuartzLog;
import cn.caojiantao.system.query.QuartzQuery;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IQuartzService {

    Quartz getQuartzById(int id);

    int countQuartzs(QuartzQuery query);

    List<Quartz> getQuartzs(QuartzQuery query);

    boolean addQuartz(Quartz quartz);

    boolean deleteQuartzById(int id);

    boolean updateQuartz(Quartz quartz);

    /**
     * 更改任务状态
     */
    boolean changeStatus(Quartz quartz);

    void addQuartzLog(QuartzLog log);

    int countQuartzLog(QuartzQuery query);

    List<QuartzLog> getQuartzLog(QuartzQuery query);
}
