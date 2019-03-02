package cn.caojiantao.system.model.quartz;

import cn.caojiantao.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定时计划基本信息
 *
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Quartz extends BaseModel {

    private String group;
    private String name;
    private String cronExpression;
    private String description;
    private String jobClass;
    private Boolean status;
}
