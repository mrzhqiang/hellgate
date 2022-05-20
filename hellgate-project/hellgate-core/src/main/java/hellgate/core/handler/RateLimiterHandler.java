package hellgate.core.handler;

import com.google.common.util.concurrent.RateLimiter;
import hellgate.core.config.SessionProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import static org.springframework.web.context.request.RequestAttributes.REFERENCE_SESSION;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
@Order(1)
public class RateLimiterHandler {

    private final SessionProperties properties;

    public RateLimiterHandler(SessionProperties properties) {
        this.properties = properties;
    }

    @Pointcut("execution(public * hellgate.core..*Controller.*(..))")
    public void controllerPoint() {
        // aop point
    }

    @SuppressWarnings("UnstableApiUsage")
    @Around("controllerPoint()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpSession session = (HttpSession) requestAttributes.getAttribute(REFERENCE_SESSION, SCOPE_SESSION);
        // maybe not login
        if (session == null) {
            return point.proceed();
        }

        String rateLimiterKey = properties.getRateLimiter().getKeyName();
        RateLimiter rateLimiter = (RateLimiter) session.getAttribute(rateLimiterKey);
        if (rateLimiter == null) {
            double permits = properties.getRateLimiter().getPermits();
            rateLimiter = RateLimiter.create(permits);
            session.setAttribute(rateLimiterKey, rateLimiter);
        }
        boolean acquire = rateLimiter.tryAcquire();
        if (acquire) {
            return point.proceed();
        }
        return new ModelAndView("refresh");
    }
}
