package ir.haji.nutro;

import ir.haji.nutro.panel.um.UserManagementDatabaseUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * User: Saeed Hajizade
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class WebApplication {
    @Autowired
    private UserManagementDatabaseUpdater userManagementDatabaseUpdater;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }

    @Configuration
    @ComponentScan(basePackages = {"..."}, lazyInit = true)
    static class LocalConfig {
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tehran"));
        userManagementDatabaseUpdater.update();
        userManagementDatabaseUpdater.createAdminIfNotExists();
    }

    /**
     * Make the session cookie to be alive for 40 days.
     *
     * @return
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SESSION");
        serializer.setCookiePath("/api/");
        serializer.setCookieMaxAge(3600000);
        return serializer;
    }
}
