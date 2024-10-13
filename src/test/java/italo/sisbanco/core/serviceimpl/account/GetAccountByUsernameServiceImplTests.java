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

public class GetAccountByUsernameServiceImplTests {

    @Test
    @DisplayName("Deve retornar uma Account pelo username do usuário com sucesso")
    void shouldGetAccountByUsernameWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();
        String username = account.getUser().getUsername();

        when( accountServicePort.getByUsername( username ) ).thenReturn( Optional.of( account ) );

        Account account2 = accountService.getByUsername( username );

        assertNotNull( account2 );
        assertEquals( account.getId(), account2.getId() );
        assertEquals( account.getUser().getId(), account2.getUser().getId() );
        assertEquals( account.getUser().getUsername(), account2.getUser().getUsername() );
        verify( accountServicePort, times( 1 ) ).getByUsername( username );
    }

    @Test
    @DisplayName("Deve tratar exceção de conta não encontrada.")
    void shouldThrowsAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();
        String username = account.getUser().getUsername();

        when( accountServicePort.getByUsername( username ) ).thenReturn( Optional.empty() );

        BusinessException ex = assertThrows( BusinessException.class, () -> accountService.getByUsername( username ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta não encontrada.");
    }

}
