package ir.haji.nutro.conf.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by Saeed Hajizade on 9/30/2019.
 */
@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        GeneratorCacheManager manager = new GeneratorCacheManager(new GeneratorCacheManager.CacheGenerator() {
            @Override
            public Cache generateCache(String name) {
                return caffeineCacheBuilder(24, name);
            }
        });

        manager.addCache(caffeineCacheBuilder(24, "TelegramAdStats"));
        manager.addCache(caffeineCacheBuilder(24, "InstagramAdStats"));
        manager.addCache(caffeineCacheBuilder(24, "TelegramReps"));
        manager.addCache(caffeineCacheBuilder(24, "InstagramReps"));
        manager.addCache(caffeineCacheBuilder(24, "TelegramClickStats"));
        manager.addCache(caffeineCacheBuilder(24, "InstagramClickStats"));
        manager.addCache(caffeineCacheBuilder(24 * 5, "dashboard"));


        return manager;
    }

    CaffeineCache caffeineCacheBuilder(int ttlInHoures, String name) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttlInHoures, TimeUnit.HOURS)
                .maximumSize(100)
                .build());
    }
}
