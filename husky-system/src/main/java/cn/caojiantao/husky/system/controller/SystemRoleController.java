package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.dto.SystemRoleDTO;
import cn.caojiantao.husky.system.model.security.SystemRole;
import cn.caojiantao.husky.system.query.RoleQuery;
import cn.caojiantao.husky.system.service.SystemRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/role")
public class SystemRoleController extends ApiController {

    private final SystemRoleService systemRoleService;

    @Autowired
    public SystemRoleController(SystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @GetMapping("/getRoleByPage")
    public R getRoleByPage(RoleQuery query) {
        QueryWrapper<SystemRole> wrapper = Wrappers.query();
        wrapper.like("name", query.getName());
        return success(systemRoleService.page(query, wrapper));
    }

    @GetMapping("/getRoleWithMenusById")
    public R getRoleWithMenusById(int id) {
        return success(systemRoleService.getRoleWithMenusById(id));
    }

    @PostMapping("/saveRole")
    public R saveRole(@RequestBody SystemRoleDTO roleDTO) {
        Integer id = roleDTO.getId();
        if ((id == null) || (id == 0)) {

            return systemRoleService.save(roleDTO) ? success(null) : failed("新增角色失败");
        } else {
            return systemRoleService.updateRole(roleDTO) ? success(null) : failed("更新角色失败");
        }
    }

    @PostMapping("/deleteRoleById")
    public R deleteRoleById(@RequestBody SystemRole systemRole) {
        return systemRoleService.deleteRoleById(systemRole.getId()) ? success(null) : failed("删除角色失败");
    }

    @GetMapping("/getAllRoles")
    public R getAllRoles() {
        return success(systemRoleService.list());
    }
}
