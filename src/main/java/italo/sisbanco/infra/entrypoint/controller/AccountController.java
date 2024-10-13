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
import italo.sisbanco.infra.entrypoint.dto.account.BankTransactionValueRequest;
import italo.sisbanco.infra.entrypoint.dto.account.SaveAccountRequest;
import italo.sisbanco.infra.mapper.AccountMapper;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sisbanco/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @CreateAccountDoc
    @PostMapping    
    public ResponseEntity<Account> createAccount( @RequestBody SaveAccountRequest request ) {        
        Account account = accountMapper.map( request );
        account.validate();

        Account regAccount = accountService.create( account );

        return ResponseEntity.status( HttpStatus.CREATED ).body( regAccount );
    }

    @UpdateAccountDoc
    @PutMapping("/{accountId}")
    public ResponseEntity<Object> updateAccount( 
            @PathVariable UUID accountId, 
            @RequestBody SaveAccountRequest request ) {
        Account account = accountMapper.map( request );
        account.setId( accountId );

        account.validate(); 

        accountService.update( account );

        return ResponseEntity.ok().build();
    }

    @ListAccountsDoc
    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = accountService.list();
        return ResponseEntity.ok( accounts );
    }

    @GetAccountDoc
    @GetMapping("/{accountId}/get")
    public ResponseEntity<Account> get( 
            @PathVariable UUID accountId ) {
        Account account = accountService.get( accountId );
        return ResponseEntity.ok( account );
    }

    @GetByUsernameAccountDoc
    @GetMapping("/get/by-username")
    public ResponseEntity<Account> getByUsername( 
            @PathParam("username") String username ) {
        Account account = accountService.getByUsername( username );
        return ResponseEntity.ok( account );
    }

    @GetByEmailAccountDoc
    @GetMapping("/get/by-email")
    public ResponseEntity<Account> getByEmail( 
            @PathParam("email") String email ) {
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
        accountService.deposit( accountId, value );
        return ResponseEntity.ok().build();
    }

    @CashDoc
    @PatchMapping("/{accountId}/cash")
    public ResponseEntity<Object> cash(
            @PathVariable UUID accountId, 
            @RequestBody BankTransactionValueRequest request ) {

        double value = request.getValue();
        accountService.cash( accountId, value );
        return ResponseEntity.ok().build();
    }

    @TransferDoc
    @PatchMapping("/source/{sourceAccountId}/dest/{destAccountId}/transfer")
    public ResponseEntity<Object> transfer(
            @PathVariable UUID sourceAccountId, 
            @PathVariable UUID destAccountId,
            @RequestBody BankTransactionValueRequest request ) {

        double value = request.getValue();
        accountService.transfer( sourceAccountId, destAccountId, value );
        return ResponseEntity.ok().build();
    }

}
