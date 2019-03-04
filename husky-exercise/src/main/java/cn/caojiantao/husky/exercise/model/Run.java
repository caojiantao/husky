package cn.caojiantao.husky.exercise.model;

import cn.caojiantao.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@TableName("exercise_run")
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseModel {

    private Double distance;
    private Integer second;
}