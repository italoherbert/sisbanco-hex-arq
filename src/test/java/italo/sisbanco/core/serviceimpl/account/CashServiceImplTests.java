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

public class CashServiceImplTests {
    
    @Test
    @DisplayName( "Deve realizar saque com sucesso")
    void shouldMakeCashWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();
        account.setBalance( 500 );

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ));
        
        accountService.cash( account.getId(), 300 );
        assertEquals( 200, account.getBalance() );

        accountService.cash( account.getId(), 50 );
        assertEquals( 150, account.getBalance() );
    }

    @Test
    @DisplayName("Deve tratar erro de conta não encontrada.")
    void shouldThrowAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();
        account.setBalance( 500 );

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.empty() );
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.cash( account.getId(), 300 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta não encontrada." ); 
    }

    @Test
    @DisplayName("Deve tratar exceção de tentativa de sacar valor negativo.")
    void shouldThrowNegativeCashValueTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ));
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.cash( account.getId(), -1 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Tentativa de sacar valor negativo.");
    }

    @Test
    @DisplayName("Deve tratar exceção de tentativa de sacar sem saldo suficiente.")
    void shouldThrowInsufficientBalanceTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();
        account.setBalance( 100 );

        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ));
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.cash( account.getId(), 101 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Saldo insuficiente.");
    }

}
