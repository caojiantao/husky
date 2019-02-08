package cn.caojiantao.system.controller;

import cn.caojiantao.system.model.security.Menu;
import cn.caojiantao.system.service.IMenuService;
import com.github.caojiantao.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/security/menu")
public class MenuController {

    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/getAllMenus")
    public ResultDTO getAllMenus() {
        return ResultDTO.success(menuService.getAllMenus());
    }

    @GetMapping("/getMenuById")
    public ResultDTO getMenuById(Integer id) {
        return ResultDTO.success(menuService.getMenuById(id));
    }

    @PostMapping("/saveMenu")
    public ResultDTO saveMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu) ? ResultDTO.success() : ResultDTO.failure("保存失败");
    }

    @PostMapping("/deleteMenuById")
    public ResultDTO deleteMenuById(@RequestBody Menu menu) {
        return menuService.deleteMenuById(menu.getId()) ? ResultDTO.success() : ResultDTO.failure("删除失败");
    }

    @GetMapping("/getMenusByUserId")
    public ResultDTO getMenusByUserId(int userId) {
        return ResultDTO.success(menuService.getMenusByUserId(userId));
    }
}
