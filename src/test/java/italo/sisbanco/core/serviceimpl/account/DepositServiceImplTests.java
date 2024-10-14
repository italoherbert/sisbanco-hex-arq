package italo.sisbanco.core.serviceimpl.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
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

public class DepositServiceImplTests {
    
    @Test
    @DisplayName("Deve realizar um depósito com sucesso.")
    void shouldMakeDepositoWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ));
        when( accountServicePort.save( account ) ).thenReturn( account );

        Account savedAccount = accountService.deposit( account.getId(), 500 );
        assertEquals( 500, savedAccount.getBalance() );

        savedAccount = accountService.deposit( account.getId(), 300 );
        assertEquals( 800, savedAccount.getBalance() );      
    }

    @Test
    @DisplayName("Deve tratar erro de conta não encontrada.")
    void shouldThrowAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.empty() );
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.deposit( account.getId(), 300 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta não encontrada." ); 
    }

    @Test
    @DisplayName("Deve lançar exceção de valor de depósito negativo.")
    void shouldThrowsNegativeDepositValueTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ));
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.deposit( account.getId(), -1000 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Tentativa de depositar valor negativo." );  
    }

}
