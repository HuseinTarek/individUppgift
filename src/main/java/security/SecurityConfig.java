package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.Customizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain");
        
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> {
                log.info("Configuring authorization rules");
                authz
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/mypages/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().authenticated();
            })
            .httpBasic(httpBasic -> httpBasic
                .authenticationEntryPoint((request, response, authException) -> {
                    response.addHeader("WWW-Authenticate", "Basic realm=\"Member API\"");
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
                })
            )
            .headers(headers -> headers.frameOptions().disable())
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.warn("Access denied for user: {}", request.getUserPrincipal() != null ? 
                        request.getUserPrincipal().getName() : "anonymous");
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Access Denied");
                })
            );
            
        return http.build();
    }

    // Add this temporary user
    @Bean
    public InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) {
        log.info("Creating in-memory users");
        
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();
                
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN", "USER")
                .build();
                
        log.info("Created users: {} and {}", user.getUsername(), admin.getUsername());
        return new InMemoryUserDetailsManager(user, admin);
    }

}