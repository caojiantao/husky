package cn.caojiantao.system.controller;

import cn.caojiantao.system.model.quartz.Quartz;
import cn.caojiantao.system.model.quartz.QuartzLog;
import cn.caojiantao.system.query.QuartzQuery;
import cn.caojiantao.system.service.IQuartzService;
import com.github.caojiantao.dto.ResultDTO;
import com.github.caojiantao.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/quartz")
public class QuartzController {

    private final IQuartzService quartzService;

    @Autowired
    public QuartzController(IQuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @GetMapping("/getQuartzByPage")
    public ResultDTO getDictionaryByPage(QuartzQuery query) {
        int total = quartzService.countQuartzs(query);
        List<Quartz> quartzs = null;
        if (total > 0) {
            quartzs = quartzService.getQuartzs(query);
        }
        return ResultDTO.success(JSONUtils.toPageData(quartzs, total));
    }

    @PostMapping("/saveQuartz")
    public ResultDTO saveDictionary(@RequestBody Quartz quartz) {
        Integer id = quartz.getId();
        boolean operate;
        if (id == null || id == 0) {
            operate = quartzService.addQuartz(quartz);
        } else {
            operate = quartzService.updateQuartz(quartz);
        }
        return operate ? ResultDTO.success() : ResultDTO.failure("保存任务失败");
    }

    @PostMapping("/deleteQuartzById")
    public ResultDTO deleteQuartzById(@RequestBody Quartz quartz) {
        return quartzService.deleteQuartzById(quartz.getId()) ? ResultDTO.success() : ResultDTO.failure("删除任务失败");
    }

    @GetMapping("/getQuartzById")
    public ResultDTO getQuartzById(int id) {
        return ResultDTO.success(quartzService.getQuartzById(id));
    }

    @PostMapping("/changeStatus")
    public ResultDTO changeStatus(@RequestBody Quartz quartz) {
        return quartzService.changeStatus(quartz) ? ResultDTO.success() : ResultDTO.failure("更改状态失败");
    }

    @GetMapping("/getQuartzLogByPage")
    public ResultDTO getQuartzLogByPage(QuartzQuery query) {
        int total = quartzService.countQuartzLog(query);
        List<QuartzLog> logs = null;
        if (total > 0) {
            logs = quartzService.getQuartzLog(query);
        }
        return ResultDTO.success(JSONUtils.toPageData(logs, total));
    }
}
