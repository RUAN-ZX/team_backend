package cn.steateam.lets_team_server.service;

import cn.steateam.lets_team_server.constant.PlatformEnum;
import cn.steateam.lets_team_server.constant.RedisConstants;
import cn.steateam.lets_team_server.dto.RedisTokenUserInfoDto;
import cn.steateam.lets_team_server.exception.HeadersNotValidException;
import cn.steateam.lets_team_server.exception.TokenCheckException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具类
 *
 * @author 王赛超、STEA_YY
 */
@SuppressWarnings("unused")
@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public void expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 指定缓存失效时间戳
     *
     * @param key  键
     * @param date 失效时间戳
     */
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 删除缓存
     *
     * @param key 一个或多个键
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 获取到的对象
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存写入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    public void incr(String key, double by) {
        redisTemplate.opsForValue().increment(key, by);
    }

    public Object hGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     */
    public Map<Object, Object> hGetMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 增加后的数值
     */
    @SuppressWarnings("UnusedReturnValue")
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 哈希缓存放入
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param item    值
     */
    public void hPut(String key, String hashKey, Object item) {
        redisTemplate.opsForHash().put(key, hashKey, item);
    }

    /**
     * 获取zSet元组
     *
     * @param key   键
     * @param start 开始项
     * @param end   结束项
     * @return ZSet元组
     */
    public Set<ZSetOperations.TypedTuple<Object>> zGetTuple(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取zSet里value对应的排名
     *
     * @param key   键
     * @param value 值
     * @return 排名
     */
    public Long zGetRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取zSet里value对应的分数
     *
     * @param key   键
     * @param value 值
     * @return 分数
     */
    public Double zGetScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * zSet写入
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     */
    public void zSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * list入队
     *
     * @param key   键
     * @param value 值
     */
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * list出队
     *
     * @param key 键
     * @return 出队的对象
     */
    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public RedisTokenUserInfoDto checkAccessToken(String accessToken, String platformId) throws TokenCheckException, HeadersNotValidException {
        if (accessToken == null) {
            throw new TokenCheckException();
        }

        if (PlatformEnum.getByValue(platformId) == null) {
            throw new HeadersNotValidException();
        }
        RedisTokenUserInfoDto redisTokenUserInfoDto = (RedisTokenUserInfoDto) get(RedisConstants.PREFIX_ACCESS_TOKEN + accessToken);
        if (redisTokenUserInfoDto == null) {
            throw new TokenCheckException();
        }
        if (!platformId.equals(redisTokenUserInfoDto.getPlatformId())) {
            throw new TokenCheckException();
        }
        String currentAccessToken = (String) hGet(RedisConstants.CURRENT_ACCESS_TOKEN_KEY, redisTokenUserInfoDto.getUserId().toString() + ":" + platformId);
        if (!accessToken.equals(currentAccessToken)) {
            throw new TokenCheckException();
        }
        return redisTokenUserInfoDto;
    }
}