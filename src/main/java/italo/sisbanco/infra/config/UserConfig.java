package italo.sisbanco.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.AccountServicePort;
import italo.sisbanco.core.ports.out.UserServicePort;
import italo.sisbanco.core.serviceimpl.AccountServiceImpl;
import italo.sisbanco.core.serviceimpl.UserServiceImpl;

@Configuration
public class UserConfig {
  
    @Bean
    UserService userService( UserServicePort port ) {
        return new UserServiceImpl( port );
    }

    @Bean
    AccountService accountService( AccountServicePort port, UserService userService ) {
        return new AccountServiceImpl( port, userService );
    }

}
