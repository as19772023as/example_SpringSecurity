package ru.strebkov.example_SpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityFileChainConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeHttpRequests(
                        (authz) -> authz
                        .antMatchers(HttpMethod.GET, "/read").hasAnyAuthority("read")//hasRole("USER")
                        .antMatchers(HttpMethod.GET,"/write").hasAnyAuthority("write")
                        .antMatchers(HttpMethod.GET,"/hello").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        // @formatter:on

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails user1 = User.builder()  //withDefaultPasswordEncoder()
                .username("Oly")
                .password(encoder().encode("123"))
                //.roles("USER")
                .authorities("write", "read")
                .build();
        UserDetails user2 = User.builder()  //withDefaultPasswordEncoder()
                .username("Ivan")
                .password(encoder().encode("1234"))
                .authorities("read")
                //.roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
// игнорирует обращение по урлу, не для прода
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/resources/**");
//    }

}
