package cn.caojiantao.husky.system.entity.quartz;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 定时计划基本信息
 *
 * @author caojiantao
 */
@Data
@TableName("system_quartz")
@EqualsAndHashCode(callSuper = false)
public class SystemQuartz extends BaseEntity {

    @TableField("`group`")
    private String group;
    private String name;
    private String cronExpression;
    private String description;
    private String jobClass;
    private Boolean status;
}
