package italo.sisbanco.core.ports.in;

import java.util.List;
import java.util.UUID;

import italo.sisbanco.core.domain.Account;

public interface AccountService {

    Account create( Account account );

    Account update( UUID accountId, Account account );

    void delete( UUID accountId );

    List<Account> list();

    Account get( UUID accountId );

    Account getByUsername( String username );

    Account getByEmail( String email );
    
    void deposit( UUID accountId, double value );

    void cash( UUID accountId, double value );

    void transfer( UUID sourceAccountId, UUID destAccountId, double value );

}
