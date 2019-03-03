package cn.caojiantao.husky.exercise.model;

import cn.caojiantao.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caojiantao
 */
@Data
@TableName("exercise_pushup")
@EqualsAndHashCode(callSuper = false)
public class Pushup extends BaseModel {

    private Integer userId;
    private Integer number;
}
