package pw.nullpointer.example.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pw.nullpointer.example.cache.UserCache;
import pw.nullpointer.example.model.User;

import javax.annotation.Resource;
import java.util.concurrent.locks.LockSupport;

/**
 * @author WeJan
 * @since 2020-07-18
 */
@SpringBootTest
class UserMapperTest {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserCache userCache;

    @Test
    void testGen() {
        userMapper.createTable();
        userMapper.insert(new User().setId(1L).setName("user-1").setAge(18));
        userMapper.insert(new User().setId(2L).setName("user-2").setAge(18));
        userMapper.insert(new User().setId(3L).setName("user-3").setAge(18));

        long userId = 1L;
        User user = userCache.get(userId);
        assert user != null;

        userCache.invalidate(userId);
        user = userCache.getIfPresent(userId);
        assert user == null;

        user = userCache.get(userId);
        assert user != null;

        user = userCache.get(userId, k -> userMapper.findById(k));
        assert user != null;

        userMapper.dropTable();
        LockSupport.parkUntil(3000);
    }

    @Test
    void testInvalidate() {
        LockSupport.park();
    }

}