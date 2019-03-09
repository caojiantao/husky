package cn.caojiantao.huksy.system.dto;

import cn.caojiantao.huksy.system.model.security.User;
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
