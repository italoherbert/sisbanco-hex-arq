package italo.sisbanco.core.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import italo.sisbanco.core.domain.Account;

public interface AccountServicePort {
    
    Account save( Account account );

    void save( Account sourceAccount, Account destAccount );

    void delete( UUID accountId );

    Optional<Account> get( UUID accountId );

    Optional<Account> getByUsername( String username );

    Optional<Account> getByEmail( String email );

    boolean exists( UUID accountId );

    List<Account> list();

}
