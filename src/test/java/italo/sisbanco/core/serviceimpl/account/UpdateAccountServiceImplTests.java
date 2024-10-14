package italo.sisbanco.core.serviceimpl.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
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

public class UpdateAccountServiceImplTests {
    
    @Test
    @DisplayName("Deve atualizar a Account com sucesso.")
    void shouldUpdateAccountWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        doNothing().when( userService ).prepareUserForUpdate( account.getUser() );
        when( accountServicePort.save( account ) ).thenReturn( account );
        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.of( account ));

        accountService.update( account.getId(), account );        
        verify( userService, times( 1 ) ).prepareUserForUpdate( account.getUser() );
        verify( accountServicePort, times( 1 ) ).save( account );
    }

    @Test
    @DisplayName("Deve tratar exceção de conta não encontrada.")
    void shouldThrowAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();
        when( accountServicePort.get( account.getId() ) ).thenReturn( Optional.empty() );

        BusinessException ex = assertThrows( BusinessException.class, 
            () -> accountService.update( account.getId(), account ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Conta não encontrada.");
    }

}
