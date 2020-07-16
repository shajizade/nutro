package ir.haji.nutro.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Saeed Hajizade on 10/1/2019.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheIt {
    /**
     * Cache Name
     *
     * @return
     */
    String value() default "";

    /**
     * Validation Time in Seconds. Cached Element will be returned after this time but the cache will be updated async
     *
     * @return
     */
    int refreshTime() default -1;

    /**
     * Alive Time in Seconds. Cached Element won't be returned after this time
     *
     * @return
     */
    int expirationTime() default -1;

}
