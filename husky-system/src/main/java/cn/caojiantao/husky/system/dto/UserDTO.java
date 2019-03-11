package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.model.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends User {

    private List<RoleDTO> roleDTOS;
}
