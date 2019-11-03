package com.portiaxe.config;


import com.portiaxe.config.filter.JwtTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    // This method is used for override HttpSecurity of the web Application.
    // We can specify our authorization criteria inside this method.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // starts authorizing configurations
        http.authorizeRequests()
                // ignore the "/" and "/index.html"
                .antMatchers("/",
                        "/login").permitAll()
                // authenticate all remaining URLS
                .anyRequest().authenticated().and()
                //.anyRequest().permitAll().and()
                // enabling the basic authentication
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                //allow Cross Origin
                .cors().and()
                // disabling the CSRF - Cross Site Request Forgery
                .csrf().disable();

        // disable page caching
        http.headers().cacheControl();



    }
    @Override
    public void configure(WebSecurity web){
        web.httpFirewall(allowEncodedSlashHttpFirewall())
                .ignoring().antMatchers("/**.css","/**.js","/**.ico","/assets/**");
    }
    @Bean
    public HttpFirewall allowEncodedSlashHttpFirewall(){
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return  firewall;
    }
}
