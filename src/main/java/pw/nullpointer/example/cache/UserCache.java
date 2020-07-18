package pw.nullpointer.example.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pw.nullpointer.example.mapper.UserMapper;
import pw.nullpointer.example.model.User;
import pw.nullpointer.zcache.cache.AbstractCache;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author WeJan
 * @since 2020-07-18
 */
@Slf4j
@Component
public class UserCache extends AbstractCache<Long, User> {
    private Cache<Long, User> cache = Caffeine.newBuilder()
            .initialCapacity(1)
            .maximumSize(500)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    @Resource
    private UserMapper userMapper;

    @Nonnull
    @Override
    protected Cache<Long, User> getCache() {
        return cache;
    }

    @Nonnull
    @Override
    protected Function<? super Long, ? extends User> getLoadFunction() {
        return key -> {
            log.info("load cache from db. key: {}", key);
            return userMapper.findById(key);
        };
    }
}
