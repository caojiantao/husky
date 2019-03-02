package cn.caojiantao.starter.interceptor;

import cn.caojiantao.system.LoginContext;
import cn.caojiantao.system.model.security.User;
import cn.caojiantao.system.service.IUserService;
import com.alibaba.fastjson.JSON;
import com.github.caojiantao.dto.ResultDTO;
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

    private final IUserService userService;

    @Autowired
    public AuthInterceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String tokenStr = request.getHeader("X-Token");
        int userId = userService.parseToken(tokenStr);
        if (userId == 0) {
            ResultDTO result = ResultDTO.failure("用户未登录");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        } else {
            User curUser = userService.getUserByUserId(userId);
            LoginContext.setUser(curUser);
            return true;
        }
    }
}
