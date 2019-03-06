package cn.caojiantao.husky.exercise.model;

import cn.caojiantao.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@Data
@TableName("exercise_run")
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseModel {

    private Integer userId;
    @TableField("`time`")
    private LocalDateTime time;
    private Double distance;
    private Integer second;
}
