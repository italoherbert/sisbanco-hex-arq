package italo.sisbanco.core.serviceimpl.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.exception.BusinessException;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.AccountServicePort;
import italo.sisbanco.core.serviceimpl.AccountServiceImpl;
import italo.sisbanco.mocks.AccountMocks;

public class GetAccountByIdServiceImplTests {
    
    @Test
    @DisplayName("Deve retornar uma Account pelo email do usuário com sucesso")
    void shouldGetAccountByEmailWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ) );

        Account account2 = accountService.get( account.getId() );

        assertNotNull( account2 );
        assertEquals( account.getId(), account2.getId() );
        assertEquals( account.getUser().getId(), account2.getUser().getId() );
        verify( accountServicePort, times( 1 ) ).get( account.getId() );
    }

    @Test
    @DisplayName("Deve tratar exceção de conta não encontrada")
    void shouldThrowsAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.empty() );

        BusinessException ex = assertThrows( BusinessException.class, () -> accountService.get( account.getId() ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta não encontrada.");        
    }

}
