package italo.sisbanco.infra.mapper;

import org.springframework.stereotype.Component;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.infra.entrypoint.dto.account.SaveAccountRequest;
import italo.sisbanco.infra.persistence.entity.AccountEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    
    private final UserMapper userMapper;

    public Account map( AccountEntity entity ) {
        return Account.builder()
            .id( entity.getId() )
            .balance( entity.getBalance() )
            .user( userMapper.map( entity.getUser() ) ) 
            .build();
    }

    public AccountEntity map( Account account ) {
        return AccountEntity.builder()
            .id( account.getId() )
            .balance( account.getBalance() )
            .user( userMapper.map( account.getUser() ) )
            .build();
    }

    public Account map( SaveAccountRequest request ) {
        return Account.builder()
            .user( userMapper.map( request.getUser() ) )
            .build();
    }

}
