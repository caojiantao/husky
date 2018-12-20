package cn.caojiantao.system.controller;

import cn.caojiantao.system.LoginContext;
import cn.caojiantao.system.dto.UserDTO;
import cn.caojiantao.system.model.security.User;
import cn.caojiantao.system.query.UserQuery;
import cn.caojiantao.system.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.github.caojiantao.dto.ResultDTO;
import com.github.caojiantao.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResultDTO login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User loginUser = userService.login(username, password);
        if (loginUser != null) {
            JSONObject result = new JSONObject();
            result.put("token", userService.generateToken(loginUser.getId()));
            result.put("user", loginUser);
            return ResultDTO.success(result);
        } else {
            return ResultDTO.failure("用户名或密码错误");
        }
    }

    @GetMapping("/getCurrentUser")
    public ResultDTO getCurrentUser() {
        return ResultDTO.success(LoginContext.getUser());
    }

    @GetMapping("/getUserByPage")
    public ResultDTO getUserByPage(UserQuery query) {
        int total = userService.countUsers(query);
        List<User> users = null;
        if (total > 0) {
            users = userService.getUsers(query);
        }
        return ResultDTO.success(JSONUtils.toPageData(users, total));
    }

    @GetMapping("/getUserWithRolesById")
    public ResultDTO getUserWithRolesById(int id) {
        return ResultDTO.success(userService.getUserWithRolesById(id));
    }

    @PostMapping("/saveUser")
    public ResultDTO saveRole(@RequestBody UserDTO userDTO) {
        Integer id = userDTO.getId();
        if ((id == null) || (id == 0)) {
            return userService.addUser(userDTO) ? ResultDTO.success() : ResultDTO.failure("新增角色失败");
        } else {
            return userService.updateUser(userDTO) ? ResultDTO.success() : ResultDTO.failure("更新角色失败");
        }
    }

    @PostMapping("/deleteUserById")
    public ResultDTO deleteUserById(@RequestBody User user) {
        return userService.deleteUserById(user.getId()) ? ResultDTO.success() : ResultDTO.failure("删除角色失败");
    }
}
