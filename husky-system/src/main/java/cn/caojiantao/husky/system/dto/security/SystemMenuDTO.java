package cn.caojiantao.husky.system.dto.security;

import cn.caojiantao.husky.system.model.security.SystemMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemMenuDTO extends SystemMenu {

    private List<SystemMenuDTO> children;
}
