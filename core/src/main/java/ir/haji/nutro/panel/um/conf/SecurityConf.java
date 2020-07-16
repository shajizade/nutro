package ir.haji.nutro.panel.um.conf;


import ir.haji.nutro.panel.um.dto.ListSuccessHandler;
import ir.haji.nutro.panel.um.entity.Permission;
import ir.haji.nutro.panel.um.predefined.UnauthorizedRole;
import ir.haji.nutro.panel.um.repo.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.List;
import java.util.Objects;

/**
 * Created by Saeed Hajizade on 1/14/17.
 */

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
public class SecurityConf extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(SecurityConf.class);
    private static BCryptPasswordEncoder bCryptPasswordEncoder = null;

    public static BCryptPasswordEncoder getPasswordEncoder() {
        if (bCryptPasswordEncoder == null)
            bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    List<SimpleUrlAuthenticationSuccessHandler> successHandlers;

    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionEvaluatorImp permissionEvaluatorImp;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(SecurityConf.getPasswordEncoder());
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationFailureHandler restAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler restAuthenticationSuccessHandler() {
        return new RESTAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler restLogoutSuccessHandler() {
        return new RESTLogoutSuccessHandler();
    }

    HttpMethod convertStringToHttpMethod(String method) {
        method = method.replace("[", "");
        method = method.replace("]", "");
        method = method.replace(" ", "");
        for (String methodMember : method.split(",")) {
            for (HttpMethod httpMethod : HttpMethod.values()) {
                if (Objects.equals(httpMethod.toString(), method)) {
                    return httpMethod;
                }
            }
        }
        return null;
    }

    // Work around https://jira.spring.io/browse/SEC-2855
    @Bean
    public SessionRegistry sessionRegistry() {
        logger.info("Session Registery Bean");
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    // Register HttpSessionEventPublisher
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("User Management Security Config Started");
        for (SimpleUrlAuthenticationSuccessHandler successHandler : successHandlers) {
            logger.info("Add login success handler: " + successHandler.getClass().getName());
        }
        AuthenticationSuccessHandler successHandler;
        if (successHandlers.size() == 0)
            successHandler = restAuthenticationSuccessHandler();
        else
            successHandler = new ListSuccessHandler(successHandlers);
        http.authorizeRequests()
                .antMatchers("/swagger*").permitAll()
                .antMatchers("/configuration/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .and().httpBasic()
                .and().formLogin()
                .successHandler(successHandler).failureHandler(restAuthenticationFailureHandler())
                .and().logout()
                .logoutSuccessHandler(restLogoutSuccessHandler())
                .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
                .and().csrf().disable()
        ;

        for (Permission permission : permissionRepository.findAll()) {
            String[] permissionNameArray = permission.getName().split("::");
            String method = permissionNameArray[0];
            String path = permissionNameArray[1];
            String unAuthorizedField = "";
            if (permissionNameArray.length > 2)
                unAuthorizedField = permissionNameArray[2];
            if (unAuthorizedField.equals(UnauthorizedRole.NAME)) {
                http.authorizeRequests().antMatchers(convertStringToHttpMethod(method), path).permitAll();
            } else {
                http.authorizeRequests().antMatchers(convertStringToHttpMethod(method), path).access("@permissionEvaluatorImp.hasPermission(authentication,null,'" + permission.getName() + "')")
                        .antMatchers(convertStringToHttpMethod(method), path + "/").access("@permissionEvaluatorImp.hasPermission(authentication,null,'" + permission.getName() + "')")
                        .and().httpBasic()
                        .and().formLogin()
                        .successHandler(successHandler).failureHandler(restAuthenticationFailureHandler())
                        .and().logout()
                        .logoutSuccessHandler(restLogoutSuccessHandler())
                        .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
                        .and().csrf().disable();
            }
        }
        logger.info("User Management Security Config Finished");
    }
}
