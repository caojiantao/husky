package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.entity.security.SystemUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUserDTO extends SystemUser {

    private List<SystemRoleDTO> roleDTOS;
}
