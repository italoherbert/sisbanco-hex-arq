package italo.sisbanco.core.serviceimpl.account;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.Account;
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
        doNothing().when( accountServicePort ).update( account );

        accountService.update( account );        
        verify( userService, times( 1 ) ).prepareUserForUpdate( account.getUser() );
        verify( accountServicePort, times( 1 ) ).update( account );
    }

}
