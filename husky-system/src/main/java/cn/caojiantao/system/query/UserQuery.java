package cn.caojiantao.system.query;

import cn.caojiantao.common.base.BaseQuery;
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

    /**
     * 构造器模式，方便拓展参数
     */
    public static class Builder {

        private int id;
        private String username;
        private String password;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserQuery build() {
            return new UserQuery(id, username, password);
        }
    }
}
