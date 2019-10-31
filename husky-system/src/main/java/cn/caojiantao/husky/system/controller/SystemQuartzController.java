package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.model.quartz.SystemQuartz;
import cn.caojiantao.husky.system.model.quartz.SystemQuartzLog;
import cn.caojiantao.husky.system.query.QuartzQuery;
import cn.caojiantao.husky.system.service.SystemQuartzService;
import cn.caojiantao.husky.system.service.SystemQuartzLogService;
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
public class SystemQuartzController extends ApiController {

    private final SystemQuartzService systemQuartzService;
    private final SystemQuartzLogService systemQuartzLogService;

    @Autowired
    public SystemQuartzController(SystemQuartzService systemQuartzService, SystemQuartzLogService systemQuartzLogService) {
        this.systemQuartzService = systemQuartzService;
        this.systemQuartzLogService = systemQuartzLogService;
    }

    @GetMapping("/getQuartzByPage")
    public R getDictionaryByPage(QuartzQuery query) {
        return success(systemQuartzService.page(query));
    }

    @PostMapping("/saveQuartz")
    public R saveDictionary(@RequestBody SystemQuartz systemQuartz) {
        Integer id = systemQuartz.getId();
        boolean operate;
        if (id == null || id == 0) {
            systemQuartz.setStatus(false);
            systemQuartz.setGmtCreate(LocalDateTime.now());
            operate = systemQuartzService.save(systemQuartz);
        } else {
            systemQuartz.setGmtModified(LocalDateTime.now());
            operate = systemQuartzService.updateById(systemQuartz);
        }
        return operate ? success(null) : failed("保存任务失败");
    }

    @PostMapping("/deleteQuartzById")
    public R deleteQuartzById(@RequestBody SystemQuartz systemQuartz) {
        return systemQuartzService.removeById(systemQuartz.getId()) ? success(null) : failed("删除任务失败");
    }

    @GetMapping("/getQuartzById")
    public R getQuartzById(int id) {
        return success(systemQuartzService.getById(id));
    }

    @PostMapping("/changeStatus")
    public R changeStatus(@RequestBody SystemQuartz systemQuartz) {
        return systemQuartzService.changeStatus(systemQuartz) ? success(null) : failed("更改状态失败");
    }

    @GetMapping("/getQuartzLogByPage")
    public R getQuartzLogByPage(Page<SystemQuartzLog> page) {
        return success(systemQuartzLogService.page(page));
    }
}
