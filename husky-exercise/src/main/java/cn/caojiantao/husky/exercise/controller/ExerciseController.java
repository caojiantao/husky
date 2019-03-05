package cn.caojiantao.husky.exercise.controller;

import cn.caojiantao.husky.exercise.model.Chinning;
import cn.caojiantao.husky.exercise.model.Pushup;
import cn.caojiantao.husky.exercise.model.Run;
import cn.caojiantao.husky.exercise.model.Situp;
import cn.caojiantao.husky.exercise.service.ChinningService;
import cn.caojiantao.husky.exercise.service.PushupService;
import cn.caojiantao.husky.exercise.service.RunService;
import cn.caojiantao.husky.exercise.service.SitupService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/exercise")
public class ExerciseController extends ApiController {

    private final ChinningService chinningService;
    private final PushupService pushupService;
    private final RunService runService;
    private final SitupService situpService;

    @Autowired
    public ExerciseController(ChinningService chinningService, PushupService pushupService, RunService runService, SitupService situpService) {
        this.chinningService = chinningService;
        this.pushupService = pushupService;
        this.runService = runService;
        this.situpService = situpService;
    }

    @GetMapping("/getChinningByPage")
    public R getChinningByPage(Page<Chinning> page) {
        return success(chinningService.page(page));
    }

    @PostMapping("/saveChinning")
    public R saveChinning(@RequestBody Chinning chinning) {
        return chinningService.save(chinning) ? success(null) : failed(ApiErrorCode.FAILED);
    }

    @GetMapping("/getPushupByPage")
    public R getPushupByPage(Page<Pushup> page) {
        return success(pushupService.page(page));
    }

    @GetMapping("/getPushupById")
    public R getPushupById(int id) {
        return success(pushupService.getById(id));
    }

    @PostMapping("/savePushup")
    public R savePushup(@RequestBody Pushup pushup) {
        return pushupService.saveOrUpdate(pushup) ? success(null) : failed(ApiErrorCode.FAILED);
    }

    @PostMapping("/deletePushupById")
    public R deletePushupById(@RequestBody Pushup pushup) {
        return pushupService.removeById(pushup.getId()) ? success(null) : failed(ApiErrorCode.FAILED);
    }

    @GetMapping("/getRunByPage")
    public R getRunByPage(Page<Run> page) {
        return success(runService.page(page));
    }

    @PostMapping("/saveRun")
    public R saveRun(@RequestBody Run run) {
        return runService.save(run) ? success(null) : failed(ApiErrorCode.FAILED);
    }

    @GetMapping("/geSitupByPage")
    public R geSitupByPage(Page<Situp> page) {
        return success(situpService.page(page));
    }

    @PostMapping("/saveSitup")
    public R saveRun(@RequestBody Situp situp) {
        return situpService.save(situp) ? success(null) : failed(ApiErrorCode.FAILED);
    }
}
