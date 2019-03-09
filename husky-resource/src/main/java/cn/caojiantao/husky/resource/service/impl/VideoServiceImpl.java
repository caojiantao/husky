package cn.caojiantao.husky.resource.service.impl;

import cn.caojiantao.husky.resource.entity.Video;
import cn.caojiantao.husky.resource.mapper.VideoMapper;
import cn.caojiantao.husky.resource.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频 服务实现类
 * </p>
 *
 * @author caojiantao
 * @since 2019-03-08
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

}
