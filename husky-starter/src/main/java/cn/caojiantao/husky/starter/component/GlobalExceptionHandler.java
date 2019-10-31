package cn.caojiantao.husky.starter.component;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author caojiantao
 * @date 2018-10-24 15:58:37
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public R handleBindException(Exception e) {
        log.error("入参校验异常：", e);
        if (e instanceof BindException) {
            return R.failed(((BindException) e).getFieldError().getDefaultMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            return R.failed(((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage());
        }
        return R.failed("未知异常");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("全局异常捕获：", e);
        return R.failed(e.getMessage());
    }
}
