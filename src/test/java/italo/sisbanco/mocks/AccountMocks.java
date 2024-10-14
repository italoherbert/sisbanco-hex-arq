package italo.sisbanco.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.infra.entrypoint.dto.account.BankTransactionValueRequest;
import italo.sisbanco.infra.entrypoint.dto.account.CreateAccountRequest;
import italo.sisbanco.infra.entrypoint.dto.account.SaveAccountRequest;
import italo.sisbanco.infra.persistence.entity.AccountEntity;

public class AccountMocks {
    
    public static Account createAccount() {
        return Account.builder()
            .id( UUID.randomUUID() )
            .balance( 0 )
            .user( UserMocks.createUser() ) 
            .build();
    }

    public static AccountEntity createAccountEntity() {
        return AccountEntity.builder()
            .id( UUID.randomUUID() )
            .balance( 0 )
            .user( UserMocks.createUserEntity() ) 
            .build();
    }

    public static CreateAccountRequest createCreateAccountRequest() {
        return CreateAccountRequest.builder()            
            .user( UserMocks.createCreateUserRequest() )
            .build();
    }

    public static SaveAccountRequest createSaveAccountRequest() {
        return SaveAccountRequest.builder()            
            .user( UserMocks.createSaveUserRequest() )
            .build();
    }

    public static BankTransactionValueRequest createValueRequest( double value ) {
        return BankTransactionValueRequest.builder()
            .value( value )
            .build();
    }

    public static List<Account> createAccountList( int size ) {
        List<Account> accounts = new ArrayList<>();
        for( int i = 0; i < size; i++ )
            accounts.add( createAccount() );
        return accounts;
    }

}
