package cn.caojiantao.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author caojiantao
 */
@Data
@AllArgsConstructor
public class ResultDTO<T> {

    private Integer code;
    private T data;
    private String msg;
}
