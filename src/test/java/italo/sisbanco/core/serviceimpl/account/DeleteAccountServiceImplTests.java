package italo.sisbanco.core.serviceimpl.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.exception.BusinessException;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.AccountServicePort;
import italo.sisbanco.core.serviceimpl.AccountServiceImpl;
import italo.sisbanco.mocks.AccountMocks;

public class DeleteAccountServiceImplTests {
    
    @Test
    @DisplayName("Deve deletar uma Account com sucesso.")
    void shouldDeleteAccountWithSuccessTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.exists( account.getId() ) ).thenReturn( true );
        doNothing().when( accountServicePort ).delete( account.getId() );

        accountService.delete( account.getId() );        
    }

    @Test
    @DisplayName("Deve retornar mensagem de Account não encontrada.")
    void shouldThrowsAccountNotFoundTest() {
        AccountServicePort accountServicePort = mock( AccountServicePort.class );        
        UserService userService = mock( UserService.class );
        AccountService accountService = new AccountServiceImpl( accountServicePort, userService );

        Account account = AccountMocks.createAccount();

        when( accountServicePort.exists( account.getId() ) ).thenReturn( false );
        doNothing().when( accountServicePort ).delete( account.getId() );

        BusinessException ex = assertThrows( BusinessException.class, () -> accountService.delete( account.getId() ) );
        assertNotNull( ex );
        assertEquals( ex.error(), "Conta não encontrada." );
    }

}
