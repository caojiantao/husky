package cn.caojiantao.system.query;

import com.github.caojiantao.dto.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DictionaryQuery extends BaseQuery {

    private String code;
    private Integer setId;
}
