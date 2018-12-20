package cn.caojiantao.system.dto;

import cn.caojiantao.system.model.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends User {

    private List<RoleDTO> roleDTOS;
}
