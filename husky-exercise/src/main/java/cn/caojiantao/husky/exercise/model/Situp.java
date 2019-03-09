package cn.caojiantao.husky.exercise.model;

import cn.caojiantao.husky.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@Data
@TableName("exercise_situp")
@EqualsAndHashCode(callSuper = false)
public class Situp extends BaseModel {

    private Integer userId;
    private LocalDate date;
    private LocalDateTime time;
    private Integer number;
}
