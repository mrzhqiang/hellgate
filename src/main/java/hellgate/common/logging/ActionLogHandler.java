package hellgate.common.logging;

import com.github.mrzhqiang.helper.StackTraces;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import hellgate.common.Authentications;
import hellgate.common.Jacksons;
import hellgate.common.third.ThirdApi;
import hellgate.common.third.WhoisIpData;
import io.reactivex.observers.DefaultObserver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

    private static final String UNKNOWN_ACTION = "(unknown-action)";
    private static final String UNKNOWN_ADDRESS = "(unknown-address)";
    private static final String PARAMETERS_TEMPLATE = "%s=%s";
    private static final char DOT_CHAR = ',';

    private final ActionLogRepository repository;
    private final ThirdApi thirdApi;

    public ActionLogHandler(ActionLogRepository repository, ThirdApi thirdApi) {
        this.repository = repository;
        this.thirdApi = thirdApi;
    }

    @Pointcut("@annotation(hellgate.common.logging.Action)")
    public void actionPoint() {
    }

    @AfterReturning(pointcut = "actionPoint()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) {
        handleAction(point, result, null);
    }

    @AfterThrowing(pointcut = "actionPoint()", throwing = "throwable")
    public void afterThrowing(JoinPoint point, Throwable throwable) {
        handleAction(point, null, throwable);
    }

    public void handleAction(JoinPoint point, Object result, Throwable throwable) {
        Method targetMethod = ((MethodSignature) point.getSignature()).getMethod();
        Action methodAction = AnnotationUtils.findAnnotation(targetMethod, Action.class);
        String action = Optional.ofNullable(methodAction).map(Action::value).orElse(UNKNOWN_ACTION);
        ActionType type = Optional.ofNullable(methodAction).map(Action::type).orElse(ActionType.NONE);

        Class<?> targetClass = point.getTarget().getClass();
        String target = Optional.ofNullable(targetClass.getCanonicalName()).orElseGet(targetClass::getName);
        String method = targetMethod.getName();

        Parameter[] parameters = targetMethod.getParameters();
        Object[] args = point.getArgs();
        List<String> paramList = Lists.newArrayListWithCapacity(parameters.length);
        for (int i = 0; i < parameters.length && i < args.length; i++) {
            String name = parameters[i].getName();
            Object arg = args[i];
            paramList.add(Strings.lenientFormat(PARAMETERS_TEMPLATE, name, arg));
        }
        String params = Joiner.on(DOT_CHAR).join(paramList);

        ActionState state = ActionState.UNKNOWN;
        String resultContent = "(no-content)";
        if (result != null) {
            state = ActionState.SUCCESS;
            resultContent = Jacksons.prettyPrint(Jacksons.toJson(result));
        }
        if (throwable != null) {
            state = ActionState.FAILED;
            resultContent = StackTraces.of(throwable);
        }

        ActionLog log = new ActionLog();
        log.setAction(action);
        log.setType(type);
        log.setTarget(target);
        log.setMethod(method);
        log.setParams(params);
        log.setState(state);
        log.setResult(resultContent);
        log.setOperator(Authentications.currentUsername());
        log.setIp(Authentications.currentHost());
        repository.save(log);
        thirdApi.whoisIp(ThirdApi.IP_LOCATION, log.getIp(), true)
                .subscribe(new DefaultObserver<WhoisIpData>() {
                    @Override
                    public void onNext(@Nonnull WhoisIpData data) {
                        log.setAddress(data.getAddr());
                        repository.save(log);
                    }

                    @Override
                    public void onError(@Nonnull Throwable e) {
                        log.setAddress(UNKNOWN_ADDRESS);
                        repository.save(log);
                    }

                    @Override
                    public void onComplete() {
                        // do nothing
                    }
                });
    }
}
