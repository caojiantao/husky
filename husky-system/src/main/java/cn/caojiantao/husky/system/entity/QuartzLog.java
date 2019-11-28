package cn.caojiantao.husky.system.entity;

import cn.caojiantao.husky.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@Data
@TableName("system_quartz_log")
@EqualsAndHashCode(callSuper = false)
public class QuartzLog extends BaseEntity {

    private Integer quartzId;
    private Boolean status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String extras;
}
