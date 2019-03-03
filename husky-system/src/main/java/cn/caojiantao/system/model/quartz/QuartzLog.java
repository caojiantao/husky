package cn.caojiantao.system.model.quartz;

import cn.caojiantao.common.base.BaseModel;
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
public class QuartzLog extends BaseModel {

    private Integer quartzId;
    private Boolean status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String exceptionMessage;
}
