package ru.strebkov.example_SpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { //  с версии 5.7.0. Spring  устарел
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Oly")
                .password(encoder().encode("password1"))
                .authorities("read", "write")
                .and()
                .withUser("Ivan")
                .password(encoder().encode("password2"))
                .authorities("read")
                .and()
                .withUser("Vasy")
                .password(encoder().encode("password3"))
                .authorities("write");

        //  auth.jdbcAuthentication().dataSource(тутнаша БД ).authoritiesByUsernameQuery().getUserDetailsService(извлечение паролей)
        //  если через проперти, то этот метод удалить
        // без метода и без проперти,  пароль шенерируется на консоли - user + пароль
        // WebSecurityConfigurerAdapter  устарел,  SecurityFilterChain
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().
                and()
                .authorizeHttpRequests().antMatchers(HttpMethod.GET, "/read").hasAnyAuthority("read")
                .and()
                .authorizeHttpRequests().antMatchers(HttpMethod.GET, "/write").hasAnyAuthority("write")
                .and()
                .authorizeHttpRequests().antMatchers(HttpMethod.GET, "/hi").permitAll()
                .and().authorizeHttpRequests().anyRequest().authenticated();
        // http://localhost:8080/login?logout  выдаст фпейм авторизации
    }
}















