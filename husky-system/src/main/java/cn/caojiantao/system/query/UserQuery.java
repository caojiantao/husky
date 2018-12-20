package cn.caojiantao.system.query;

import com.github.caojiantao.dto.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息DTO
 *
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserQuery extends BaseQuery {

    private Integer id;
    private String username;
    private String password;
}
