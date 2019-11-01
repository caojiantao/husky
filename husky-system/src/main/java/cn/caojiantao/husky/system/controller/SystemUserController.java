package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.LoginContext;
import cn.caojiantao.husky.system.dto.SystemUserDTO;
import cn.caojiantao.husky.system.model.security.SystemUser;
import cn.caojiantao.husky.system.query.UserQuery;
import cn.caojiantao.husky.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/user")
public class SystemUserController extends ApiController {

    private final UserService userService;

    @Autowired
    public SystemUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public R login(@Valid @RequestBody SystemUser systemUser) {
        String username = systemUser.getUsername();
        String password = systemUser.getPassword();
        SystemUser loginUser = userService.login(username, password);
        if (loginUser != null) {
            try {
                String token = userService.generateToken(loginUser.getId());
                return success(token);
            } catch (Exception e) {
                logger.error("", e);
                return failed("登录异常，请稍后重试");
            }
        } else {
            return failed("用户名或密码错误");
        }
    }

    @GetMapping("/getCurrentUser")
    public R getCurrentUser() {
        return success(LoginContext.getUser());
    }

    @GetMapping("/getUserByPage")
    public R getUserByPage(UserQuery query) {
        QueryWrapper<SystemUser> wrapper = Wrappers.query();
        wrapper.like("username", query.getUsername());
        return success(userService.page(query, wrapper));
    }

    @GetMapping("/getUserWithRolesById")
    public R getUserWithRolesById(int id) {
        return success(userService.getUserWithRolesById(id));
    }

    @PostMapping("/saveUser")
    public R saveRole(@RequestBody SystemUserDTO userDTO) {
        Integer id = userDTO.getId();
        boolean operate;
        if ((id == null) || (id == 0)) {
            operate = userService.save(userDTO);
        } else {
            operate = userService.updateUser(userDTO);
        }
        return operate ? success(null) : failed("保存用户失败");
    }

    @PostMapping("/deleteUserById")
    public R deleteUserById(@RequestBody SystemUser systemUser) {
        return userService.removeById(systemUser.getId()) ? success(null) : failed("删除用户失败");
    }
}
