package knk.erp.api.shlee.interceptor;

import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        logger.info("==================== START ====================");
        try {
            logger.info("요청 토큰: {}", token.substring(7));
        }catch (Exception e){
            logger.info("요청 토큰 없음");
        }
        logger.info("request_host: {}:{}\trequest_url: {}", request.getRemoteHost(), request.getRemotePort(), request.getRemoteAddr());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("===================== END =====================");
        super.postHandle(request, response, handler, modelAndView);
    }
}
