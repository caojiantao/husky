package cn.caojiantao.husky.resource.service.impl;

import cn.caojiantao.husky.resource.entity.Audio;
import cn.caojiantao.husky.resource.mapper.AudioMapper;
import cn.caojiantao.husky.resource.service.IAudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 音频 服务实现类
 * </p>
 *
 * @author caojiantao
 * @since 2019-03-08
 */
@Service
public class AudioServiceImpl extends ServiceImpl<AudioMapper, Audio> implements IAudioService {

}
