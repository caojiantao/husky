package cn.caojiantao.base.model.quartz;

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

    private String name;
    private String group;
    private Boolean status;
    private String cronExpression;
    private String desc;
    private String jobClass;
    private Boolean deleted;
}
