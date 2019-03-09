package cn.caojiantao.huksy.system.controller;

import cn.caojiantao.huksy.system.model.quartz.Quartz;
import cn.caojiantao.huksy.system.model.quartz.QuartzLog;
import cn.caojiantao.huksy.system.query.QuartzQuery;
import cn.caojiantao.huksy.system.service.QuartzService;
import cn.caojiantao.huksy.system.service.QuartzLogService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/quartz")
public class QuartzController extends ApiController {

    private final QuartzService quartzService;
    private final QuartzLogService quartzLogService;

    @Autowired
    public QuartzController(QuartzService quartzService, QuartzLogService quartzLogService) {
        this.quartzService = quartzService;
        this.quartzLogService = quartzLogService;
    }

    @GetMapping("/getQuartzByPage")
    public R getDictionaryByPage(QuartzQuery query) {
        return success(quartzService.page(query));
    }

    @PostMapping("/saveQuartz")
    public R saveDictionary(@RequestBody Quartz quartz) {
        Integer id = quartz.getId();
        boolean operate;
        if (id == null || id == 0) {
            quartz.setStatus(false);
            quartz.setGmtCreate(LocalDateTime.now());
            operate = quartzService.save(quartz);
        } else {
            quartz.setGmtModified(LocalDateTime.now());
            operate = quartzService.updateById(quartz);
        }
        return operate ? success(null) : failed("保存任务失败");
    }

    @PostMapping("/deleteQuartzById")
    public R deleteQuartzById(@RequestBody Quartz quartz) {
        return quartzService.removeById(quartz.getId()) ? success(null) : failed("删除任务失败");
    }

    @GetMapping("/getQuartzById")
    public R getQuartzById(int id) {
        return success(quartzService.getById(id));
    }

    @PostMapping("/changeStatus")
    public R changeStatus(@RequestBody Quartz quartz) {
        return quartzService.changeStatus(quartz) ? success(null) : failed("更改状态失败");
    }

    @GetMapping("/getQuartzLogByPage")
    public R getQuartzLogByPage(Page<QuartzLog> page) {
        return success(quartzLogService.page(page));
    }
}
