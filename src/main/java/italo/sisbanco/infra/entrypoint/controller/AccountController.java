package italo.sisbanco.infra.entrypoint.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.ports.in.AccountService;
import italo.sisbanco.infra.entrypoint.apidoc.account.CashDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.CreateAccountDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.DeleteAccountDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.DepositDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.GetAccountDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.GetByEmailAccountDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.GetByUsernameAccountDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.ListAccountsDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.TransferDoc;
import italo.sisbanco.infra.entrypoint.apidoc.account.UpdateAccountDoc;
import italo.sisbanco.infra.entrypoint.dto.account.BalanceResponse;
import italo.sisbanco.infra.entrypoint.dto.account.BankTransactionValueRequest;
import italo.sisbanco.infra.entrypoint.dto.account.CreateAccountRequest;
import italo.sisbanco.infra.entrypoint.dto.account.SaveAccountRequest;
import italo.sisbanco.infra.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sisbanco/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @CreateAccountDoc
    @PostMapping    
    public ResponseEntity<Account> createAccount( @RequestBody CreateAccountRequest request ) {        
        Account account = accountMapper.map( request );
        Account regAccount = accountService.create( account );
        return ResponseEntity.status( HttpStatus.CREATED ).body( regAccount );
    }

    @UpdateAccountDoc
    @PutMapping("/{accountId}")
    public ResponseEntity<Object> updateAccount( 
            @PathVariable UUID accountId, 
            @RequestBody SaveAccountRequest request ) {

        Account account = accountMapper.map( request );
        Account updatedAccount = accountService.update( accountId, account );
        return ResponseEntity.ok( updatedAccount );
    }

    @ListAccountsDoc
    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = accountService.list();
        return ResponseEntity.ok( accounts );
    }

    @GetAccountDoc
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> get( 
            @PathVariable UUID accountId ) {
        Account account = accountService.get( accountId );
        return ResponseEntity.ok( account );
    }

    @GetByUsernameAccountDoc
    @GetMapping("/by-username")
    public ResponseEntity<Account> getByUsername( 
            @RequestParam("username") String username ) {
        Account account = accountService.getByUsername( username );
        return ResponseEntity.ok( account );
    }

    @GetByEmailAccountDoc
    @GetMapping("/by-email")
    public ResponseEntity<Account> getByEmail( 
            @RequestParam("email") String email ) {
        Account account = accountService.getByEmail( email );
        return ResponseEntity.ok( account );
    }

    @DeleteAccountDoc
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Object> deleteAccount( @PathVariable UUID accountId ) {
        accountService.delete( accountId );
        return ResponseEntity.ok().build();
    }

    @DepositDoc
    @PatchMapping("/{accountId}/deposit")
    public ResponseEntity<Object> deposit( 
            @PathVariable UUID accountId, 
            @RequestBody BankTransactionValueRequest request ) {

        double value = request.getValue();
        Account account = accountService.deposit( accountId, value );

        BalanceResponse resp = BalanceResponse.builder()
            .balance( account.getBalance() )
            .build();

        return ResponseEntity.ok( resp );
    }

    @CashDoc
    @PatchMapping("/{accountId}/cash")
    public ResponseEntity<Object> cash(
            @PathVariable UUID accountId, 
            @RequestBody BankTransactionValueRequest request ) {

        double value = request.getValue();
        Account account = accountService.cash( accountId, value );
        
        BalanceResponse resp = BalanceResponse.builder()
            .balance( account.getBalance() )
            .build();

        return ResponseEntity.ok( resp );
    }

    @TransferDoc
    @PatchMapping("/source/{sourceAccountId}/dest/{destAccountId}/transfer")
    public ResponseEntity<Object> transfer(
            @PathVariable UUID sourceAccountId, 
            @PathVariable UUID destAccountId,
            @RequestBody BankTransactionValueRequest request ) {

        double value = request.getValue();
        Account sourceAccount = accountService.transfer( sourceAccountId, destAccountId, value );

        BalanceResponse resp = BalanceResponse.builder()
            .balance( sourceAccount.getBalance() )
            .build();

        return ResponseEntity.ok( resp );
    }

}
