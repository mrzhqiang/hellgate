package com.github.mrzhqiang.hellgate.logging;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 操作日志的处理器。
 * <p>
 * 使用 AOP 切面完成日志入库。
 */
@Slf4j
@Aspect
@Component
public class ActionLogHandler {

    private final ActionLogRepository repository;

    public ActionLogHandler(ActionLogRepository repository) {
        this.repository = repository;
    }


    @Around("@annotation(com.github.mrzhqiang.hellgate.logging.Action)")
    public Object handleLog(ProceedingJoinPoint point) throws Throwable {
        Class<?> targetClass = point.getTarget().getClass();
        Method targetMethod = ClassUtils.getMethod(targetClass, point.getSignature().getName());
        Action methodAction = AnnotationUtils.findAnnotation(targetMethod, Action.class);

        String action = Optional.ofNullable(methodAction).map(Action::value).orElse("unknown action");
        String target = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, targetClass.getSimpleName());
        String method = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, targetMethod.getName());

        Parameter[] parameters = targetMethod.getParameters();
        Object[] args = point.getArgs();
        List<String> paramList = Lists.newArrayListWithCapacity(parameters.length);
        for (int i = 0; i < parameters.length && i < args.length; i++) {
            paramList.add(Strings.lenientFormat("%s=%s", parameters[i].getName(), args[i]));
        }
        String params = Joiner.on(',').join(paramList);

        LocalDateTime start = LocalDateTime.now();
        Object proceed = point.proceed();
        Duration elapsed = Duration.between(start, LocalDateTime.now());

        ActionLog log = new ActionLog();
        log.setAction(action);
        log.setTarget(target);
        log.setMethod(method);
        log.setParams(params);
        log.setElapsed(elapsed);
        repository.save(log);
        return proceed;
    }
}
