package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.entity.Menu;
import cn.caojiantao.husky.system.service.MenuService;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/getMenuListAsTree")
    public R getMenuListAsTree() {
        return success(menuService.getMenuListAsTree());
    }

    @GetMapping("/getPersonalMenuListAsTree")
    public R getPersonalMenuListAsTree() {
        return success(menuService.getPersonalMenuListAsTree());
    }

    @PostMapping("/saveMenu")
    public R saveMenu(@Valid @RequestBody Menu menu) {
        boolean operate;
        if (menu.getId() == null || menu.getId() == 0) {
            menu.setGmtCreate(LocalDateTime.now());
            operate = menuService.save(menu);
        } else {
            menu.setGmtModified(LocalDateTime.now());
            operate = menuService.updateById(menu);
        }
        return operate ? success(null) : failed(ApiErrorCode.FAILED);
    }

    @PostMapping("/deleteMenuById")
    public R deleteMenuById(@RequestBody Menu menu) {
        return menuService.deleteByMenuId(menu.getId()) ? success(null) : failed(ApiErrorCode.FAILED);
    }
}
