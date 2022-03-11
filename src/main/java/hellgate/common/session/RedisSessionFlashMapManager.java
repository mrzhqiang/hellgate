package hellgate.common.session;

import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

public final class RedisSessionFlashMapManager extends SessionFlashMapManager {

    @Override
    protected Object getFlashMapsMutex(@Nonnull HttpServletRequest request) {
        // 不使用互斥锁，保证 Redis 解析 Session 时，不会因为 CopyOnWriteArrayList 而导致解析失败
        return null;
    }
}
