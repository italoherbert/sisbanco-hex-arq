package italo.sisbanco.infra.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.infra.entrypoint.dto.account.BankTransactionValueRequest;
import italo.sisbanco.infra.entrypoint.dto.account.CreateAccountRequest;
import italo.sisbanco.infra.entrypoint.dto.account.SaveAccountRequest;
import italo.sisbanco.mocks.AccountMocks;

@SpringBootTest
public class AccountControllerTests {
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup( webApplicationContext )
            .build();
    }

    @Test
    @DisplayName("Deve criar uma conta com sucesso.")
    void shouldCreateAccountWithSuccessTest() throws Exception {
        CreateAccountRequest request = AccountMocks.createCreateAccountRequest();

        String content = new ObjectMapper().writeValueAsString( request );

        mockMvc.perform( 
            post("/api/sisbanco/v1/accounts" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content ) )
            .andExpect( status().isCreated() );
            
        verify( accountService, times( 1 ) ).create( any( Account.class ) );
    }

    @Test
    @DisplayName("Deve atualizar uma conta com sucesso.")
    void shouldUpdateAccountWithSuccessTest() throws Exception {
        SaveAccountRequest request = AccountMocks.createSaveAccountRequest();
        UUID accountId = UUID.randomUUID();

        String content = new ObjectMapper().writeValueAsString( request );      
        
        mockMvc.perform( 
            put("/api/sisbanco/v1/accounts/"+accountId )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content ) )
            .andExpect( status().isOk() );
            
        verify( accountService, times( 1 ) ).update( eq( accountId ), any( Account.class ) );
    }

    @Test
    @DisplayName("Deve remover uma conta com sucesso.")
    void shouldRemoveAccountWithSuccessTest() throws Exception {
        UUID accountId = UUID.randomUUID();

        mockMvc.perform( 
            delete("/api/sisbanco/v1/accounts/"+accountId ) )
            .andExpect( status().isOk() );
            
        verify( accountService, times( 1 ) ).delete( accountId );
    }

    @Test
    @DisplayName("Deve retornar a lista de contas.")
    void shouldListAccountWithSuccessTest() throws Exception {
        mockMvc.perform( 
            get("/api/sisbanco/v1/accounts" ) )                
            .andExpect( status().isOk() ); 
            
        verify( accountService, times( 1 ) ).list();
    }

    @Test
    @DisplayName("Deve retornar uma conta com sucesso.")
    void shouldGetAccountWithSuccessTest() throws Exception {
        UUID accountId = UUID.randomUUID();

        mockMvc.perform( 
            get("/api/sisbanco/v1/accounts/"+accountId+"/get" ) )                
            .andExpect( status().isOk() );            

        verify( accountService, times( 1 ) ).get( accountId );
    }

    @Test
    @DisplayName("Deve retornar uma conta por username com sucesso.")
    void shouldGetAccountByUsernameWithSuccessTest() throws Exception {
        Account account = AccountMocks.createAccount();
        String username = account.getUser().getUsername(); 

        mockMvc.perform( 
            get("/api/sisbanco/v1/accounts/get/by-username" )
                .param( "username", username ) )                
            .andExpect( status().isOk() );     
            
        verify( accountService, times( 1 ) ).getByUsername( username );
    }

    @Test
    @DisplayName("Deve retornar uma conta por email com sucesso.")
    void shouldGetAccountByEmailWithSuccessTest() throws Exception {
        Account account = AccountMocks.createAccount();
        String email = account.getUser().getEmail(); 

        mockMvc.perform( 
            get("/api/sisbanco/v1/accounts/get/by-email" )
                .param( "email", email ) )                
            .andExpect( status().isOk() );            

        verify( accountService, times( 1 ) ).getByEmail( email );
    }

    @Test
    @DisplayName("Deve realizar depósito com sucesso.")
    void shouldDepositValueWithSuccessTest() throws Exception {
        double value = 100;

        BankTransactionValueRequest request = AccountMocks.createValueRequest( value );
        UUID accountId = UUID.randomUUID();

        String content = new ObjectMapper().writeValueAsString( request );

        mockMvc.perform( 
            patch( "/api/sisbanco/v1/accounts/"+accountId+"/deposit" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content ) )
            .andExpect( status().isOk() );

        verify( accountService, times( 1 ) ).deposit( accountId, value );
    }

    @Test
    @DisplayName("Deve realizar saque com sucesso.")
    void shouldCashValueWithSuccessTest() throws Exception {
        double value = 100;
        BankTransactionValueRequest request = AccountMocks.createValueRequest( value );
        UUID accountId = UUID.randomUUID();

        String content = new ObjectMapper().writeValueAsString( request );

        mockMvc.perform( 
            patch( "/api/sisbanco/v1/accounts/"+accountId+"/cash" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content ) )
            .andExpect( status().isOk() );
        
        verify( accountService, times( 1 ) ).cash( accountId, value );
    }

    @Test
    @DisplayName("Deve realizar saque com sucesso.")
    void shouldTransferValueWithSuccessTest() throws Exception {
        double value = 100;
        BankTransactionValueRequest request = AccountMocks.createValueRequest( value );
        UUID sourceAccountId = UUID.randomUUID();
        UUID destAccountId = UUID.randomUUID();

        String content = new ObjectMapper().writeValueAsString( request );

        mockMvc.perform( 
            patch( "/api/sisbanco/v1/accounts/source/"+sourceAccountId+"/dest/"+destAccountId+"/transfer" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( content ) )
            .andExpect( status().isOk() );

        verify( accountService, times( 1 ) ).transfer( sourceAccountId, destAccountId, value );        
    }

}
