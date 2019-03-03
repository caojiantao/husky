package cn.caojiantao.system.controller;

import cn.caojiantao.system.LoginContext;
import cn.caojiantao.system.dto.UserDTO;
import cn.caojiantao.system.model.security.Role;
import cn.caojiantao.system.model.security.User;
import cn.caojiantao.system.query.UserQuery;
import cn.caojiantao.system.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.caojiantao.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/security/user")
public class UserController extends ApiController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User loginUser = userService.login(username, password);
        if (loginUser != null) {
            JSONObject result = new JSONObject();
            result.put("token", userService.generateToken(loginUser.getId()));
            result.put("user", loginUser);
            return success(result);
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
        QueryWrapper<User> wrapper = Wrappers.query();
        wrapper.like("username", query.getUsername());
        return success(userService.page(query, wrapper));
    }

    @GetMapping("/getUserWithRolesById")
    public R getUserWithRolesById(int id) {
        return success(userService.getUserWithRolesById(id));
    }

    @PostMapping("/saveUser")
    public R saveRole(@RequestBody UserDTO userDTO) {
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
    public R deleteUserById(@RequestBody User user) {
        return userService.removeById(user.getId()) ? success(null) : failed("删除用户失败");
    }
}
