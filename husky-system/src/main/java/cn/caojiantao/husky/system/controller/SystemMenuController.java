package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.model.security.SystemMenu;
import cn.caojiantao.husky.system.service.SystemMenuService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/menu")
public class SystemMenuController extends ApiController {

    private final SystemMenuService systemMenuService;

    @Autowired
    public SystemMenuController(SystemMenuService systemMenuService) {
        this.systemMenuService = systemMenuService;
    }

    @GetMapping("/getMenuListAsTree")
    public R getMenuListAsTree() {
        return success(systemMenuService.getMenuListAsTree());
    }

    @GetMapping("/getMenuById")
    public R getMenuById(Integer id) {
        return success(systemMenuService.getById(id));
    }

    @PostMapping("/saveMenu")
    public R saveMenu(@RequestBody SystemMenu systemMenu) {
        boolean operate;
        if (systemMenu.getId() == null || systemMenu.getId() == 0) {
            systemMenu.setGmtCreate(LocalDateTime.now());
            operate = systemMenuService.save(systemMenu);
        } else {
            systemMenu.setGmtModified(LocalDateTime.now());
            operate = systemMenuService.updateById(systemMenu);
        }
        return operate ? success(null) : failed("保存失败");
    }

    @PostMapping("/deleteMenuById")
    public R deleteMenuById(@RequestBody SystemMenu systemMenu) {
        return systemMenuService.removeById(systemMenu.getId()) ? success(null) : failed("删除失败");
    }

    @GetMapping("/getMenusByUserId")
    public R getMenusByUserId(int userId) {
        return success(systemMenuService.getMenusByUserId(userId));
    }
}
