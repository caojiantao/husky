package cn.caojiantao.system.service;

import cn.caojiantao.system.dto.UserDTO;
import cn.caojiantao.system.model.security.User;
import cn.caojiantao.system.query.UserQuery;

import java.util.List;

/**
 * @author caojiantao
 */
public interface IUserService {

    User login(String username, String password);

    String generateToken(int userId);

    int parseToken(String token);

    User getUserByUserId(int id);

    List<User> getUsers(UserQuery query);

    int countUsers(UserQuery query);

    boolean addUser(UserDTO userDTO);

    boolean updateUser(UserDTO userDTO);

    boolean deleteUserById(int id);

    UserDTO getUserWithRolesById(int id);
}
