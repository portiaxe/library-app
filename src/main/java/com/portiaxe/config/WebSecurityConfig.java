package com.portiaxe.config;


import com.portiaxe.config.filter.JwtTokenAuthenticationFilter;
import com.portiaxe.security.CustomAuthenticationProvider;
import com.portiaxe.security.CustomSessionRegistry;
import com.portiaxe.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    public WebSecurityConfig() {
        super();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("USER");
        auth.authenticationProvider(authProvider());

    }


    // This method is used for override HttpSecurity of the web Application.
    // We can specify our authorization criteria inside this method.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // starts authorizing configurations
        http.authorizeRequests()
                // ignore the "/" and "/index.html"
                .antMatchers("/",
                        "/login", "/test-login", "/password").permitAll()
                .antMatchers("/test-message").authenticated()
                // authenticate all remaining URLS
                .anyRequest().authenticated().and()
                //.anyRequest().permitAll().and()
                // enabling the basic authentication
                .formLogin().and()
                .httpBasic().and()
                //allow Cross Origin
                .cors().and()
                // disabling the CSRF - Cross Site Request Forgery
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());


    }

    @Override
    public void configure(WebSecurity web) {
        web.httpFirewall(allowEncodedSlashHttpFirewall())
                .ignoring().antMatchers("/**.css", "/**.js", "/**.ico", "/assets/**");
    }

    @Bean
    public HttpFirewall allowEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new CustomSessionRegistry();
    }

    @Bean
    public CustomAuthenticationProvider authProvider(){
        CustomAuthenticationProvider authenticationProvider =  new CustomAuthenticationProvider();
        return  authenticationProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}