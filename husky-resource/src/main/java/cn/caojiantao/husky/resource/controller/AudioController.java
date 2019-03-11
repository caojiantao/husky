package cn.caojiantao.husky.resource.controller;


import cn.caojiantao.husky.common.service.QiniuService;
import cn.caojiantao.husky.resource.entity.Audio;
import cn.caojiantao.husky.resource.query.AudioQuery;
import cn.caojiantao.husky.resource.service.IAudioService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 音频 前端控制器
 * </p>
 *
 * @author caojiantao
 * @since 2019-03-08
 */
@RestController
@RequestMapping("/resource/audio")
public class AudioController extends ApiController {

    private final IAudioService audioService;
    private final QiniuService qiniuService;

    @Autowired
    public AudioController(IAudioService audioService, QiniuService qiniuService) {
        this.audioService = audioService;
        this.qiniuService = qiniuService;
    }

    @PostMapping("/saveAudio")
    public R saveAudio(@RequestBody Audio audio) {
        audio.setPlayUrl(qiniuService.simplifyUrl(audio.getPlayUrl()));
        return audioService.saveOrUpdate(audio) ? success(null) : failed("保存音频失败");
    }

    @PostMapping("/deleteAudioById")
    public R deleteAudioById(@RequestBody Audio audio) {
        return audioService.removeById(audio.getId()) ? success(null) : failed("删除音频失败");
    }

    @GetMapping("/getAudioByPage")
    public R getAudioByPage(AudioQuery query) {
        QueryWrapper<Audio> wrapper = Wrappers.query();
        wrapper.like("name", query.getName());
        IPage<Audio> page = audioService.page(query, wrapper);
        List<Audio> records = page.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(record -> record.setPlayUrl(qiniuService.decorateUrl(record.getPlayUrl())));
        }
        return success(page);
    }

    @GetMapping("/getAudioById")
    public R getAudioById(Integer id) {
        Audio audio = audioService.getById(id);
        audio.setPlayUrl(qiniuService.decorateUrl(audio.getPlayUrl()));
        return success(audio);
    }
}
