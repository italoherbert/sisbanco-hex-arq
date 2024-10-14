package italo.sisbanco.core.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.exception.BusinessException;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.AccountServicePort;

public class AccountServiceImpl implements AccountService {

    private final AccountServicePort accountServicePort;
    private final UserService userService;

    public AccountServiceImpl( AccountServicePort port, UserService userService ) {
        this.accountServicePort = port;
        this.userService = userService;
    }

    @Override
    public Account create( Account account ) { 
        account.validate();

        userService.prepareUserForCreate( account.getUser() );

        account.setBalance( 0 ); 

        return accountServicePort.save( account );
    }

    @Override
    public Account update(UUID accountId, Account account) {
        Optional<Account> accountOp = accountServicePort.get( accountId );
        if ( !accountOp.isPresent() )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );

        Account account2 = accountOp.get();
        account2.getUser().setFirstname( account.getUser().getFirstname() );
        account2.getUser().setLastname( account.getUser().getLastname() );
        account2.getUser().setEmail( account.getUser().getEmail() );
        account2.getUser().setUsername( account.getUser().getUsername() ); 

        account2.validate();

        userService.prepareUserForUpdate( account2.getUser() );
        
        return accountServicePort.save( account2 ); 
    }

    @Override
    public void delete(UUID accountId) {
        if ( !accountServicePort.exists( accountId ) )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );
        
        accountServicePort.delete( accountId );
    }

    public List<Account> list() {
        return accountServicePort.list();
    }

    @Override
    public Account getByEmail(String email) {
        Optional<Account> accountOp = accountServicePort.getByEmail( email );
        if ( !accountOp.isPresent() )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );
        return accountOp.get();
    }

    @Override
    public Account getByUsername(String username) {
        Optional<Account> accountOp = accountServicePort.getByUsername( username );
        if ( !accountOp.isPresent() )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );
        return accountOp.get();
    }

    @Override
    public Account get(UUID accountId) {
        Optional<Account> accountOp = accountServicePort.get( accountId );
        if ( !accountOp.isPresent() )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );
        return accountOp.get();
    }

    @Override
    public Account deposit(UUID accountId, double value) {
        if ( value < 0 )
            throw new BusinessException( BusinessException.NEGATIVE_DEPOSIT );

        Optional<Account> accountOp = accountServicePort.get( accountId );
        if ( !accountOp.isPresent() )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );

        Account account = accountOp.get();        

        double balance = account.getBalance();
        account.setBalance( balance + value );

        return accountServicePort.save( account ); 
    }

    @Override
    public Account cash(UUID accountId, double value) {
        if ( value < 0 )
            throw new BusinessException( BusinessException.NEGATIVE_CASH );

        Optional<Account> accountOp = accountServicePort.get( accountId );
        if ( !accountOp.isPresent() )
            throw new BusinessException( BusinessException.ACCOUNT_NOT_FOUND );

        Account account = accountOp.get();

        double balance = account.getBalance();
        double newValue = balance - value;

        account.setBalance( newValue ); 

        if ( newValue < 0 )
            throw new BusinessException( BusinessException.INSUFFICIENT_BALANCE );

        return accountServicePort.save( account );
    }

    @Override
    public Account transfer(UUID sourceAccountId, UUID destAccountId, double value) {
        if ( value < 0 )
            throw new BusinessException( BusinessException.NEGATIVE_TRANSFER_VALUE );

        if ( sourceAccountId.equals( destAccountId ) )
            throw new BusinessException( BusinessException.EQUALS_SOURCE_AND_DEST_ACCOUNTS );

        Optional<Account> sourceAccountOp = accountServicePort.get( sourceAccountId );
        if ( !sourceAccountOp.isPresent() )
            throw new BusinessException( BusinessException.SOURCE_ACCOUNT_NOT_FOUND );

        Optional<Account> destAccountOp = accountServicePort.get( destAccountId );
        if ( !destAccountOp.isPresent() )
            throw new BusinessException( BusinessException.DEST_ACCOUNT_NOT_FOUND );

        Account sourceAccount = sourceAccountOp.get();
        Account destAccount = destAccountOp.get();

        double sourceBalance = sourceAccount.getBalance();
        double newSourceBalance = sourceBalance - value;

        if ( newSourceBalance < 0 )
            throw new BusinessException( BusinessException.INSUFFICIENT_BALANCE );

        double destBalance = destAccount.getBalance();
        double newDestBalance = destBalance + value;

        sourceAccount.setBalance( newSourceBalance );
        destAccount.setBalance( newDestBalance ); 

        accountServicePort.save( sourceAccount, destAccount );        
        return sourceAccount;
    }

}
