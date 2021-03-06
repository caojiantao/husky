package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.configuration.TokenConfig;
import cn.caojiantao.husky.system.dto.RoleDTO;
import cn.caojiantao.husky.system.dto.UserDTO;
import cn.caojiantao.husky.system.entity.User;
import cn.caojiantao.husky.system.mapper.UserMapper;
import cn.caojiantao.husky.system.mapper.UserRoleMapper;
import cn.caojiantao.husky.system.entity.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

/**
 * @author caojiantao
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    private final TokenConfig tokenConfig;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserService(UserMapper userMapper, UserRoleMapper userRoleMapper, TokenConfig tokenConfig) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.tokenConfig = tokenConfig;
    }

    public User login(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, encryptPassword(password));
    }

    /**
     * 生成token（注意注意：key为exp必须为非负数！！！）
     */
    public String generateToken(int userId) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(tokenConfig.getSecret());
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenConfig.getExpired()))
                .sign(algorithm);
    }

    public int parseToken(String token) throws UnsupportedEncodingException, JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(tokenConfig.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("userId").asInt();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(UserDTO userDTO) {
        userDTO.setPassword(encryptPassword(userDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setGmtCreate(LocalDateTime.now());
        userMapper.insert(user);
        initUserRole(user.getId(), userDTO.getRoleDTOS());
        return user.getId() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserDTO userDTO) {
        userDTO.setPassword(encryptPassword(userDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setGmtModified(LocalDateTime.now());
        initUserRole(user.getId(), userDTO.getRoleDTOS());
        return userMapper.updateById(user) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(int id) {
        userRoleMapper.deleteByUserId(id);
        return userMapper.deleteById(id) > 0;
    }

    public UserDTO getUserWithRolesById(int id) {
        User user = userMapper.selectById(id);
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
