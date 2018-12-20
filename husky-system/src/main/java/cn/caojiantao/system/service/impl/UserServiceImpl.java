package cn.caojiantao.system.service.impl;

import cn.caojiantao.system.dto.RoleDTO;
import cn.caojiantao.system.dto.UserDTO;
import cn.caojiantao.system.mapper.security.UserMapper;
import cn.caojiantao.system.mapper.security.UserRoleMapper;
import cn.caojiantao.system.model.security.User;
import cn.caojiantao.system.model.security.UserRole;
import cn.caojiantao.system.query.UserQuery;
import cn.caojiantao.system.service.IUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.caojiantao.util.ExceptionUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    private String secret = "secret";
    private long expire = 7 * 24 * 60 * 60 * 1000;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public User login(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, encryptPassword(password));
    }

    /**
     * 生成token（注意注意：key为exp必须为非负数！！！）
     */
    @Override
    public String generateToken(int userId) {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", userId)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                    .sign(algorithm);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return token;
    }

    @Override
    public int parseToken(String token) {
        int userId = 0;
        if (!Strings.isNullOrEmpty(token)) {
            try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build();
                DecodedJWT jwt = verifier.verify(token);
                userId = jwt.getClaim("userId").asInt();
            } catch (JWTVerificationException | UnsupportedEncodingException e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return userId;
    }

    @Override
    public User getUserByUserId(int id) {
        return userMapper.getById(id);
    }

    @Override
    public List<User> getUsers(UserQuery query) {
        return userMapper.getObjects(query);
    }

    @Override
    public int countUsers(UserQuery query) {
        return userMapper.countObjects(query);
    }

    @Override
    @Transactional
    public boolean addUser(UserDTO userDTO) {
        userDTO.setPassword(encryptPassword(userDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setGmtCreate(LocalDateTime.now());
        userMapper.insert(user);
        initUserRole(user.getId(), userDTO.getRoleDTOS());
        return user.getId() > 0;
    }

    @Override
    @Transactional
    public boolean updateUser(UserDTO userDTO) {
        userDTO.setPassword(encryptPassword(userDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setGmtModified(LocalDateTime.now());
        initUserRole(user.getId(), userDTO.getRoleDTOS());
        return userMapper.updateById(user) > 0;
    }

    @Override
    @Transactional
    public boolean deleteUserById(int id) {
        userRoleMapper.deleteByUserId(id);
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public UserDTO getUserWithRolesById(int id) {
        User user = userMapper.getById(id);
        if (user == null) {
            return null;
        } else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            List<Integer> roleIds = userRoleMapper.getRoleIdsByUserId(id);
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<RoleDTO> roleDTOS = roleIds.stream().map(roleId -> {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setId(roleId);
                    return roleDTO;
                }).collect(Collectors.toList());
                userDTO.setRoleDTOS(roleDTOS);
            }
            return userDTO;
        }
    }

    /**
     * 对密码进行MD5私盐不可逆加密
     */
    private String encryptPassword(String password) {
        if (Strings.isNullOrEmpty(password)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    private void initUserRole(Integer userId, List<RoleDTO> roleDTOS) {
        if (userId != null && userId > 0) {
            userRoleMapper.deleteByUserId(userId);
            if (!CollectionUtils.isEmpty(roleDTOS)) {
                List<UserRole> userRoles = roleDTOS.stream().map(roleDTO -> new UserRole(userId, roleDTO.getId())).collect(Collectors.toList());
                userRoleMapper.addUserRoles(userRoles);
            }
        }
    }
}
