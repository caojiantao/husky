package cn.caojiantao.husky.starter.interceptor;

import cn.caojiantao.husky.system.LoginContext;
import cn.caojiantao.husky.system.configuration.TokenConfig;
import cn.caojiantao.husky.system.entity.security.SystemUser;
import cn.caojiantao.husky.system.service.UserService;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caojiantao
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenConfig tokenConfig;
    private final UserService userService;

    @Autowired
    public AuthInterceptor(UserService userService, TokenConfig tokenConfig) {
        this.userService = userService;
        this.tokenConfig = tokenConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String tokenStr = request.getHeader(tokenConfig.getKey());
        String message;
        try {
            int userId = userService.parseToken(tokenStr);
            // 设置当前用户信息
            SystemUser curSystemUser = userService.getById(userId);
            LoginContext.setUser(curSystemUser);
            return true;
        } catch (TokenExpiredException e) {
            message = "用户凭证已过期";
        } catch (Exception e) {
            message = "未知异常";
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(R.failed(message)));
        return false;
    }
}
