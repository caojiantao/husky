package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.dto.RoleDTO;
import cn.caojiantao.husky.system.entity.Role;
import cn.caojiantao.husky.system.query.RoleQuery;
import cn.caojiantao.husky.system.service.RoleService;
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
public class RoleController extends ApiController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoleByPage")
    public R getRoleByPage(RoleQuery query) {
        QueryWrapper<Role> wrapper = Wrappers.query();
        wrapper.like("name", query.getName());
        return success(roleService.page(query, wrapper));
    }

    @GetMapping("/getRoleWithMenusById")
    public R getRoleWithMenusById(int id) {
        return success(roleService.getRoleWithMenusById(id));
    }

    @PostMapping("/saveRole")
    public R saveRole(@RequestBody RoleDTO roleDTO) {
        Integer id = roleDTO.getId();
        if ((id == null) || (id == 0)) {

            return roleService.save(roleDTO) ? success(null) : failed("新增角色失败");
        } else {
            return roleService.updateRole(roleDTO) ? success(null) : failed("更新角色失败");
        }
    }

    @PostMapping("/deleteRoleById")
    public R deleteRoleById(@RequestBody Role role) {
        return roleService.deleteRoleById(role.getId()) ? success(null) : failed("删除角色失败");
    }

    @GetMapping("/getAllRoles")
    public R getAllRoles() {
        return success(roleService.list());
    }
}
