package cn.caojiantao.husky.resource.entity;

import cn.caojiantao.husky.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 音频
 * </p>
 *
 * @author caojiantao
 * @since 2019-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("resource_audio")
public class Audio extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 音频名称
     */
    private String name;

    /**
     * 时长（S）
     */
    private Integer duration;

    /**
     * 音频封面
     */
    private String cover;

    /**
     * 地址
     */
    private String playUrl;

    /**
     * 大小
     */
    private Integer size;
}
