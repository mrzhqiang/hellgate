package hellgate.manage.config;

import hellgate.manage.account.Roles;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(ManageSecurityProperties.class)
@EnableWebSecurity
@Configuration
public class ManageSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private static final String ADMIN_PATH = "/admin/**";
    private static final String USER_PATH = "/user/**";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AccessDecisionVoter<?> hierarchyVoter() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(Roles.HIERARCHY);
        return new RoleHierarchyVoter(hierarchy);
    }

    @Configuration
    static class WebsiteSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final ManageSecurityProperties manageSecurityProperties;
        private final UserDetailsService userDetailsService;

        public WebsiteSecurityAdapter(ManageSecurityProperties manageSecurityProperties,
                                      UserDetailsService userDetailsService) {
            this.manageSecurityProperties = manageSecurityProperties;
            this.userDetailsService = userDetailsService;
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .antMatchers(manageSecurityProperties.getIgnorePath());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.userDetailsService(userDetailsService)
                    .authorizeRequests()
                    .antMatchers(manageSecurityProperties.getPublicPath()).permitAll()
                    .antMatchers(USER_PATH).hasRole(Roles.RAW_USER)
                    .antMatchers(ADMIN_PATH).hasRole(Roles.RAW_ADMIN)
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage(manageSecurityProperties.getLoginPath()).permitAll()
                    .defaultSuccessUrl(manageSecurityProperties.getDefaultSuccessUrl())
                    .and().logout().permitAll();
            if (manageSecurityProperties.getRememberMe()) {
                http.rememberMe();
            }
        }
    }
}
