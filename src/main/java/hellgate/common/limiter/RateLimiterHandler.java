package hellgate.common.limiter;

import com.google.common.util.concurrent.RateLimiter;
import hellgate.common.session.Sessions;
import hellgate.common.Views;
import hellgate.config.AccountProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 限流处理器。
 * <p>
 * 优先级最高。
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class RateLimiterHandler {

    private final AccountProperties accountProperties;

    public RateLimiterHandler(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }

    @Pointcut("execution(public * hellgate.controller..*Controller.*(..))")
    public void controllerPoint() {
        // aop point
    }

    @SuppressWarnings("UnstableApiUsage")
    @Around("controllerPoint()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
        HttpSession session = Sessions.ofCurrent();
        // maybe not login
        if (session == null) {
            return point.proceed();
        }

        String sessionKey = accountProperties.getRateLimiterKey();
        RateLimiter rateLimiter = (RateLimiter) session.getAttribute(sessionKey);
        if (rateLimiter == null) {
            double permits = accountProperties.getRateLimiterPermits();
            rateLimiter = RateLimiter.create(permits);
            session.setAttribute(sessionKey, rateLimiter);
        }
        boolean acquire = rateLimiter.tryAcquire();
        if (acquire) {
            return point.proceed();
        }
        return Views.ofRefresh();
    }
}
