package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.model.security.Menu;
import cn.caojiantao.husky.system.service.MenuService;
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
public class MenuController extends ApiController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/getAllMenus")
    public R getAllMenus() {
        return success(menuService.list());
    }

    @GetMapping("/getMenuById")
    public R getMenuById(Integer id) {
        return success(menuService.getById(id));
    }

    @PostMapping("/saveMenu")
    public R saveMenu(@RequestBody Menu menu) {
        boolean operate;
        if (menu.getId() == null || menu.getId() == 0) {
            menu.setGmtCreate(LocalDateTime.now());
            operate = menuService.save(menu);
        } else {
            menu.setGmtModified(LocalDateTime.now());
            operate = menuService.updateById(menu);
        }
        return operate ? success(null) : failed("保存失败");
    }

    @PostMapping("/deleteMenuById")
    public R deleteMenuById(@RequestBody Menu menu) {
        return menuService.removeById(menu.getId()) ? success(null) : failed("删除失败");
    }

    @GetMapping("/getMenusByUserId")
    public R getMenusByUserId(int userId) {
        return success(menuService.getMenusByUserId(userId));
    }
}
