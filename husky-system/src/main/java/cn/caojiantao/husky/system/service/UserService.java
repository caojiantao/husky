package cn.caojiantao.husky.system.service;

import cn.caojiantao.husky.system.dto.SystemRoleDTO;
import cn.caojiantao.husky.system.dto.SystemUserDTO;
import cn.caojiantao.husky.system.mapper.security.SystemUserMapper;
import cn.caojiantao.husky.system.mapper.security.SystemUserRoleMapper;
import cn.caojiantao.husky.system.model.security.SystemUser;
import cn.caojiantao.husky.system.model.security.SystemUserRole;
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
public class UserService extends ServiceImpl<SystemUserMapper, SystemUser> {

    private final SystemUserMapper systemUserMapper;
    private final SystemUserRoleMapper systemUserRoleMapper;

    private String secret = "secret";
    private long expire = 7 * 24 * 60 * 60 * 1000;

    @Autowired
    public UserService(SystemUserMapper systemUserMapper, SystemUserRoleMapper systemUserRoleMapper) {
        this.systemUserMapper = systemUserMapper;
        this.systemUserRoleMapper = systemUserRoleMapper;
    }

    public SystemUser login(String username, String password) {
        return systemUserMapper.getUserByUsernameAndPassword(username, encryptPassword(password));
    }

    /**
     * 生成token（注意注意：key为exp必须为非负数！！！）
     */
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
            log.error("生成token报错：", e);
        }
        return token;
    }

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
                log.error("解析token报错：", e);
            }
        }
        return userId;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(SystemUserDTO userDTO) {
        userDTO.setPassword(encryptPassword(userDTO.getPassword()));
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDTO, systemUser);
        systemUser.setGmtCreate(LocalDateTime.now());
        systemUserMapper.insert(systemUser);
        initUserRole(systemUser.getId(), userDTO.getRoleDTOS());
        return systemUser.getId() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SystemUserDTO userDTO) {
        userDTO.setPassword(encryptPassword(userDTO.getPassword()));
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(userDTO, systemUser);
        systemUser.setGmtModified(LocalDateTime.now());
        initUserRole(systemUser.getId(), userDTO.getRoleDTOS());
        return systemUserMapper.updateById(systemUser) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(int id) {
        systemUserRoleMapper.deleteByUserId(id);
        return systemUserMapper.deleteById(id) > 0;
    }

    public SystemUserDTO getUserWithRolesById(int id) {
        SystemUser systemUser = systemUserMapper.selectById(id);
        if (systemUser == null) {
            return null;
        } else {
            SystemUserDTO userDTO = new SystemUserDTO();
            BeanUtils.copyProperties(systemUser, userDTO);
            List<Integer> roleIds = systemUserRoleMapper.getRoleIdsByUserId(id);
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<SystemRoleDTO> roleDTOS = roleIds.stream().map(roleId -> {
                    SystemRoleDTO roleDTO = new SystemRoleDTO();
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

    private void initUserRole(Integer userId, List<SystemRoleDTO> roleDTOS) {
        if (userId != null && userId > 0) {
            systemUserRoleMapper.deleteByUserId(userId);
            if (!CollectionUtils.isEmpty(roleDTOS)) {
                List<SystemUserRole> systemUserRoles = roleDTOS.stream().map(roleDTO -> new SystemUserRole(userId, roleDTO.getId())).collect(Collectors.toList());
                systemUserRoleMapper.addUserRoles(systemUserRoles);
            }
        }
    }
}
