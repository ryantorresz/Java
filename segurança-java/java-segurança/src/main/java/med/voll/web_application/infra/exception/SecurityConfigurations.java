package med.voll.web_application.infra.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Bean
    public UserDetailsService registeredUsersData() {
        UserDetails user1 = User.builder()
                .username("joao@email.com")
                .password("joao123")
                .build();
        UserDetails user2 = User.builder()
                .username("maria@email.com")
                .password("maria123")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}