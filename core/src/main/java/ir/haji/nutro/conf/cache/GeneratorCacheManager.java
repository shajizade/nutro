package ir.haji.nutro.conf.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by jalil on 10/14/2019.
 */
public class GeneratorCacheManager implements CacheManager {
    HashMap<String, Cache> caches = new HashMap<>();
    CacheGenerator cacheGenerator;

    public GeneratorCacheManager(CacheGenerator cacheGenerator) {
        this.cacheGenerator = cacheGenerator;
    }

    public interface CacheGenerator {
        public Cache generateCache(String name);
    }

    @Override
    public Cache getCache(String s) {
        Cache cache = caches.get(s);
        if (cache != null)
            return cache;
        if (cacheGenerator != null) {
            cache = cacheGenerator.generateCache(s);
            if (cache != null)
                this.addCache(cache);
        }
        return cache;
    }

    public void addCache(Cache cache) {
        caches.put(cache.getName(), cache);
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }
}
