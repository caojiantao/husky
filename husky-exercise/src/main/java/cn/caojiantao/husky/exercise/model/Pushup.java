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
@TableName("exercise_pushup")
@EqualsAndHashCode(callSuper = false)
public class Pushup extends BaseModel {

    private Integer userId;
    private LocalDateTime time;
    private Integer number;
}
