package cn.caojiantao.husky.system.dto;

import cn.caojiantao.husky.system.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author caojiantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends Menu {

    private List<MenuDTO> children;
}
