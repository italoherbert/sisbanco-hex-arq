package italo.sisbanco.core.serviceimpl.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.AccountServicePort;
import italo.sisbanco.core.serviceimpl.AccountServiceImpl;
import italo.sisbanco.mocks.AccountMocks;

public class CreateAccountServiceImplTests {
    
    @Test
    @DisplayName("Deve criar uma Account com sucesso.")
    void shouldCreateAccountWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        doNothing().when( userService ).prepareUserForCreate( account.getUser() );
        when( accountServicePort.insert( account ) ).thenReturn( account );

        Account accountReg = accountService.create( account );
        assertNotNull( accountReg );
        assertEquals( accountReg.getId(), account.getId() );
        assertEquals( accountReg.getBalance(), 0 );
        verify( userService, times( 1 ) ).prepareUserForCreate( account.getUser() );
        verify( accountServicePort, times( 1 ) ).insert( account );    
    }

}
