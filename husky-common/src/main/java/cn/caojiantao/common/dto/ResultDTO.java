package cn.caojiantao.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caojiantao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> {

    private Integer code;
    private T data;
    private String msg;
}
