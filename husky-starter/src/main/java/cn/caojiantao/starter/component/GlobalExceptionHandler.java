package cn.caojiantao.starter.component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.github.caojiantao.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caojiantao
 * @date 2018-10-24 15:58:37
 * @description
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        log.error("全局异常捕获：", ex);
        try {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            String res = JSON.toJSONString(R.failed("服务器异常"));
            response.getWriter().write(res);
        } catch (IOException e) {
            log.error("写入response发生异常：", e);
        }
        return new ModelAndView();

    }
}
