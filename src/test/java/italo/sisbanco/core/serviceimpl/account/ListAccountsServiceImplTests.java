package italo.sisbanco.core.serviceimpl.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.AccountServicePort;
import italo.sisbanco.core.serviceimpl.AccountServiceImpl;
import italo.sisbanco.mocks.AccountMocks;

public class ListAccountsServiceImplTests {

    @Test
    @DisplayName("Deve retornar uma lista de Accounts")
    void shouldReturnAccountListTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        List<Account> accounts = AccountMocks.createAccountList( 5 );

        when( accountServicePort.list() ).thenReturn( accounts );

        List<Account> accounts2 = accountService.list();

        assertEquals( accounts, accounts2 );
    }

}
