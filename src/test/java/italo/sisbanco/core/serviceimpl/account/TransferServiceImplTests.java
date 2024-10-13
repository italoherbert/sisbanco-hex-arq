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

public class TransferServiceImplTests {
    
    @Test
    @DisplayName( "Deve realizar transferência com sucesso.")
    void shouldMakeTransferWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account orig = AccountMocks.createAccount();
        Account dest = AccountMocks.createAccount();
        
        orig.setBalance( 500 );
        dest.setBalance( 300 ); 

        when( accountServicePort.get( orig.getId() ) ).thenReturn( Optional.of( orig ));
        when( accountServicePort.get( dest.getId() ) ).thenReturn( Optional.of( dest ));
        
        accountService.transfer( orig.getId(), dest.getId(), 500 );

        assertEquals( 0, orig.getBalance() );
        assertEquals( 800, dest.getBalance() ); 
    }

    @Test
    @DisplayName("Deve tratar erro de conta de origem não encontrada.")
    void shouldThrowOrigAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account orig = AccountMocks.createAccount();
        Account dest = AccountMocks.createAccount();

        orig.setBalance( 500 );
        dest.setBalance( 300 ); 

        when( accountServicePort.get( orig.getId() ) ).thenReturn( Optional.empty() );
        when( accountServicePort.get( dest.getId() ) ).thenReturn( Optional.of( dest ) );
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.transfer( orig.getId(), dest.getId(), 300 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta de origem não encontrada." ); 
    }

    @Test
    @DisplayName("Deve tratar erro de conta de destino não encontrada.")
    void shouldThrowDestAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account orig = AccountMocks.createAccount();
        Account dest = AccountMocks.createAccount();

        orig.setBalance( 500 );
        dest.setBalance( 300 ); 

        when( accountServicePort.get( orig.getId() ) ).thenReturn( Optional.of( orig ) );
        when( accountServicePort.get( dest.getId() ) ).thenReturn( Optional.empty() );
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.transfer( orig.getId(), dest.getId(), 300 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta de destino não encontrada." ); 
    }

    @Test
    @DisplayName("Deve tratar exceção de tentativa de transferir valor negativo.")
    void shouldThrowNegativeCashValueTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account orig = AccountMocks.createAccount();
        Account dest = AccountMocks.createAccount();

        when( accountServicePort.get( orig.getId() ) ).thenReturn( Optional.of( orig ));
        when( accountServicePort.get( dest.getId() ) ).thenReturn( Optional.of( dest ));
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.transfer( orig.getId(), dest.getId(), -1 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Tentativa de transferir valor negativo.");
    }

    @Test
    @DisplayName("Deve tratar exceção de tentativa de transferir sem saldo suficiente.")
    void shouldThrowInsufficientBalanceTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account orig = AccountMocks.createAccount();
        Account dest = AccountMocks.createAccount();

        orig.setBalance( 100 );
        dest.setBalance( 200 );

        when( accountServicePort.get( orig.getId() ) ).thenReturn( Optional.of( orig ));
        when( accountServicePort.get( dest.getId() ) ).thenReturn( Optional.of( dest ));
        
        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.transfer( orig.getId(), dest.getId(), 101 ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Saldo insuficiente.");
    }

}
