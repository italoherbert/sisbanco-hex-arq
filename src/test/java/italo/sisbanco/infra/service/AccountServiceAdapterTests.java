package italo.sisbanco.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.infra.mapper.AccountMapper;
import italo.sisbanco.infra.persistence.entity.AccountEntity;
import italo.sisbanco.infra.persistence.repository.AccountRepository;
import italo.sisbanco.mocks.AccountMocks;

@SpringBootTest
public class AccountServiceAdapterTests {
    
    @Autowired
    private AccountServiceAdapter accountServiceAdapter;

    @Autowired
    private AccountMapper accountMapper;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Deve salvar uma Account no sistema")
    void saveAccountTest() {
        Account account = AccountMocks.createAccount();
        AccountEntity entity = accountMapper.map( account );
                
        when( accountRepository.save( any( AccountEntity.class) ) ).thenReturn( entity );
        
        Account createdAccount = accountServiceAdapter.save( account );

        assertNotNull( createdAccount );
        assertEquals( account.getId(), createdAccount.getId() );
        assertEquals( account.getBalance(), createdAccount.getBalance() );
        assertEquals( account.getUser().getId(), createdAccount.getUser().getId() );  
        verify( accountRepository, times( 1 ) ).save( any(AccountEntity.class) );
    }

    @Test
    @DisplayName("Deve salvar as contas de origem e destino de uma transação")
    void saveSourceAndDestAccountsTest() {
        Account orig = AccountMocks.createAccount();
        Account dest = AccountMocks.createAccount();

        AccountEntity origEntity = accountMapper.map( orig );
        AccountEntity destEntity = accountMapper.map( dest );

        when( accountRepository.save( origEntity ) ).thenReturn( origEntity );
        when( accountRepository.save( destEntity ) ).thenReturn( destEntity );

        accountServiceAdapter.save( orig, dest );
        
        verify( accountRepository, times( 2 ) ).save( any( AccountEntity.class ) );
    }

    @Test
    @DisplayName("Deve deletar uma Account")
    void deleteAccountTest() {
        Account account = AccountMocks.createAccount();
        
        doNothing().when( accountRepository ).deleteById( account.getId() );

        accountServiceAdapter.delete( account.getId() ); 

        verify( accountRepository, times( 1 ) ).deleteById( account.getId() );
    }

    @Test
    @DisplayName("Deve retornar uma Account pelo ID")
    void getAccountByIdTest() {
        Account account = AccountMocks.createAccount();
        AccountEntity entity = accountMapper.map( account );

        when( accountRepository.findById( account.getId() ) ).thenReturn( Optional.of( entity ) );

        Optional<Account> accountOp = accountServiceAdapter.get( account.getId() );
        assertTrue( accountOp.isPresent() );
        assertEquals( account.getId(), accountOp.get().getId() );
        verify( accountRepository, times( 1 ) ).findById( account.getId() );
    }

    @Test
    @DisplayName("Deve retornar uma Account pelo username")
    void getAccountByUsernameTest() {
        Account account = AccountMocks.createAccount();
        AccountEntity entity = accountMapper.map( account );
        String username = account.getUser().getUsername();

        when( accountRepository.findByUsername( username ) ).thenReturn( Optional.of( entity ) );

        Optional<Account> accountOp = accountServiceAdapter.getByUsername( username );
        assertTrue( accountOp.isPresent() );
        assertEquals( account.getId(), accountOp.get().getId() );
        assertEquals( account.getUser().getId(), accountOp.get().getUser().getId() );
        assertEquals( account.getUser().getUsername(), accountOp.get().getUser().getUsername() );
        verify( accountRepository, times( 1 ) ).findByUsername( username );
    }

    @Test
    @DisplayName("Deve retornar uma Account pelo email")
    void getAccountByEmailTest() {
        Account account = AccountMocks.createAccount();
        AccountEntity entity = accountMapper.map( account );
        String email = account.getUser().getEmail();

        when( accountRepository.findByEmail( email ) ).thenReturn( Optional.of( entity ) );

        Optional<Account> accountOp = accountServiceAdapter.getByEmail( email );
        assertTrue( accountOp.isPresent() );
        assertEquals( account.getId(), accountOp.get().getId() );
        assertEquals( account.getUser().getId(), accountOp.get().getUser().getId() );
        assertEquals( account.getUser().getEmail(), accountOp.get().getUser().getEmail() );
        verify( accountRepository, times( 1 ) ).findByEmail( email );
    }

    @Test
    @DisplayName("Deve retornar se uma Account existe pelo ID")
    void accountExistsByIdTest() {
        Account account = AccountMocks.createAccount();

        when( accountRepository.existsById( account.getId() ) ).thenReturn( true );

        boolean exists = accountServiceAdapter.exists( account.getId() );
        assertTrue( exists );

        when( accountRepository.existsById( account.getId() ) ).thenReturn( false );
        
        exists = accountServiceAdapter.exists( account.getId() );
        assertFalse( exists );
    }

    @Test
    @DisplayName("Deve retornar lista de Accounts")
    void listAccountsTest() {
        List<Account> accounts = AccountMocks.createAccountList( 5 );
        List<AccountEntity> entities = accounts.stream().map( a -> accountMapper.map( a ) ).toList();

        when( accountRepository.findAll() ).thenReturn( entities );

        List<Account> accounts2 = accountServiceAdapter.list();
        assertEquals( accounts, accounts2 );
    }

}
