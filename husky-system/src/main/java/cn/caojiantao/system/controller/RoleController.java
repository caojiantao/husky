package cn.caojiantao.system.controller;

import cn.caojiantao.system.dto.RoleDTO;
import cn.caojiantao.system.model.security.Role;
import cn.caojiantao.system.query.RoleQuery;
import cn.caojiantao.system.service.IRoleService;
import com.github.caojiantao.dto.ResultDTO;
import com.github.caojiantao.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/role")
public class RoleController {

    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getRoleByPage")
    public ResultDTO getRoleByPage(RoleQuery query) {
        int total = roleService.countRoles(query);
        List<Role> roles = null;
        if (total > 0) {
            roles = roleService.getRoles(query);
        }
        return ResultDTO.success(JSONUtils.toPageData(roles, total));
    }

    @GetMapping("/getRoleWithMenusById")
    public ResultDTO getRoleWithMenusById(int id) {
        return ResultDTO.success(roleService.getRoleWithMenusById(id));
    }

    @PostMapping("/saveRole")
    public ResultDTO saveRole(@RequestBody RoleDTO roleDTO) {
        Integer id = roleDTO.getId();
        if ((id == null) || (id == 0)) {
            return roleService.addRole(roleDTO) ? ResultDTO.success() : ResultDTO.failure("新增角色失败");
        } else {
            return roleService.updateRole(roleDTO) ? ResultDTO.success() : ResultDTO.failure("更新角色失败");
        }
    }

    @PostMapping("/deleteRoleById")
    public ResultDTO deleteRoleById(@RequestBody Role role) {
        return roleService.deleteRoleById(role.getId()) ? ResultDTO.success() : ResultDTO.failure("删除角色失败");
    }

    @GetMapping("/getAllRoles")
    public ResultDTO getAllRoles() {
        return ResultDTO.success(roleService.getRoles(null));
    }
}
