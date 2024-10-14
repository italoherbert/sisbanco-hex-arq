package italo.sisbanco.infra.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.infra.entrypoint.dto.account.SaveAccountRequest;
import italo.sisbanco.infra.persistence.entity.AccountEntity;
import italo.sisbanco.mocks.AccountMocks;

@SpringBootTest
public class AccountMapperTests {
    
    @Autowired
    private AccountMapper accountMapper;

    @Test
    @DisplayName("Deve mapear uma AccountEntity para uma Account")
    void shouldMapAccountEntityToAccountTest() {
        AccountEntity entity = AccountMocks.createAccountEntity();

        Account account = accountMapper.map( entity );

        assertEquals( entity.getId(), account.getId() );
        assertEquals( entity.getBalance(), account.getBalance() );
        assertEquals( entity.getUser().getId(), account.getUser().getId() );
    }

    @Test
    @DisplayName("Deve mapear uma Account para uma AccountEntity")
    void shouldMapAccountToAccountEntityTest() {
        Account account = AccountMocks.createAccount();
        AccountEntity entity = accountMapper.map( account );

        assertEquals( entity.getId(), account.getId() );
        assertEquals( entity.getBalance(), account.getBalance() );
        assertEquals( entity.getUser().getId(), account.getUser().getId() );
    }

    @Test
    @DisplayName("Deve mapear uma SaveAccountRequest para uma Account")
    void shouldMapSaveAccountRequestToAccountTest() {
        SaveAccountRequest request = AccountMocks.createSaveAccountRequest();

        Account account = accountMapper.map( request );

        assertEquals( request.getUser().getFirstname(), account.getUser().getFirstname() );
        assertEquals( request.getUser().getLastname(), account.getUser().getLastname() );
        assertEquals( request.getUser().getEmail(), account.getUser().getEmail() );
        assertEquals( request.getUser().getUsername(), account.getUser().getUsername() );
    }

}
