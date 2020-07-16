package ir.haji.nutro.cache;


import ir.haji.nutro.CommonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Saeed Hajizade on 10/1/2019.
 */
@Aspect
@Component
public class CacheAspect {
    @Autowired
    CacheManager cacheManager;
    Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    /**
     * A function that will execute method only if needed
     *
     * @param jp JointPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(ir.haji.nutro.cache.CacheIt)")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        CacheIt annotation = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(CacheIt.class);
        int validationTime = annotation.refreshTime();
        int aliveTime = annotation.expirationTime();

        Cache cache = cacheManager.getCache(annotation.value());
        if (cache == null) {
            return jp.proceed(jp.getArgs());
        }

        String key = key(jp.getArgs());
        Cache.ValueWrapper valueWrapper = cache.get(key);

        if (valueWrapper == null) {
            return proceedAndUpdate(cache, key, jp);
        }
        CacheElementWrapper elementWrapper = (CacheElementWrapper) valueWrapper.get();

        if (CommonUtil.addSecond(new Date(), -1 * aliveTime).after(elementWrapper.getInsertedTime())) {
            return proceedAndUpdate(cache, key, jp);
        }

        if (CommonUtil.addSecond(new Date(), -1 * validationTime).after(elementWrapper.getInsertedTime()))
            asyncProceedAndUpdate(cache, key, jp);
        return elementWrapper.getElement();
    }


    private void asyncProceedAndUpdate(final Cache cache, final String key, final ProceedingJoinPoint jp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    proceedAndUpdate(cache, key, jp);
                } catch (Throwable ignored) {
                }
            }
        }).start();
    }

    private Object proceedAndUpdate(Cache cache, String key, ProceedingJoinPoint jp) throws Throwable {
        Object result;
        try {
            result = jp.proceed(jp.getArgs());
        } catch (Throwable ex) {
            cache.evict(key);
            throw ex;
        }
        CacheElementWrapper wrapper = new CacheElementWrapper(result);
        cache.put(key, wrapper);
        return result;
    }

    private String key(Object[] args) {
        if (args == null || args.length == 0)
            return "key";
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append("__");
            if (arg == null) {
                builder.append("null");
            } else
                builder.append(arg.toString());
        }
        return builder.toString().replace('\r', '=').replace('\n', '-');
    }
}
