package com.github.mrzhqiang.hellgate.common.limiter;

import com.github.mrzhqiang.hellgate.common.Views;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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

    /**
     * 每个会话中持有限流器的 KEY 值。
     */
    public static final String RATE_LIMITER_KEY = "rateLimiter";
    /**
     * 限流器策略：平均每秒访问次数不超过 2 次
     */
    public static final double PERMITS_PER_SECOND = 2.0;

    @Pointcut("execution(public * com.github.mrzhqiang.hellgate.controller..*Controller.*(..))")
    public void controllerPoint() {
    }

    @SuppressWarnings("UnstableApiUsage")
    @Around("controllerPoint()")
    public Object handle(ProceedingJoinPoint point) throws Throwable {
        HttpSession session = (HttpSession) RequestContextHolder.currentRequestAttributes()
                .getAttribute(RequestAttributes.REFERENCE_SESSION, RequestAttributes.SCOPE_SESSION);
        if (session == null) {
            return point.proceed();
        }

        RateLimiter rateLimiter = (RateLimiter) session.getAttribute(RATE_LIMITER_KEY);
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(PERMITS_PER_SECOND);
            session.setAttribute(RATE_LIMITER_KEY, rateLimiter);
        }
        boolean acquire = rateLimiter.tryAcquire();
        if (acquire) {
            return point.proceed();
        }
        return Views.ofRefresh();
    }
}
