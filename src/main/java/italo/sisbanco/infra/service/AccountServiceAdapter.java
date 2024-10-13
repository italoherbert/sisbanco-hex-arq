package italo.sisbanco.infra.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import italo.sisbanco.core.domain.Account;
import italo.sisbanco.core.ports.out.AccountServicePort;
import italo.sisbanco.infra.mapper.AccountMapper;
import italo.sisbanco.infra.persistence.entity.AccountEntity;
import italo.sisbanco.infra.persistence.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceAdapter implements AccountServicePort {
    
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account insert(Account account) {
        AccountEntity entity = accountMapper.map( account );
        AccountEntity regEntity = accountRepository.save( entity );
        return accountMapper.map( regEntity );
    }

    @Override
    public void update(Account account) {
        AccountEntity entity = accountMapper.map( account );
        accountRepository.save( entity );
    }

    @Override
    @Transactional
    public void update(Account sourceAccount, Account destAccount) {
        AccountEntity sourceEntity = accountMapper.map( sourceAccount );
        AccountEntity destEntity = accountMapper.map( destAccount );
        
        accountRepository.save( sourceEntity );
        accountRepository.save( destEntity );
    }

    @Override
    public void delete(UUID userId) {
        accountRepository.deleteById( userId );
    }

    @Override
    public Optional<Account> get(UUID accountId) {        
        return accountRepository.findById( accountId ).map( accountMapper::map );
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return accountRepository.findByEmail( email ).map( accountMapper::map );
    }

    @Override
    public Optional<Account> getByUsername(String username) {
        return accountRepository.findByUsername( username ).map( accountMapper::map ); 
    }

    @Override
    public boolean exists(UUID accountId) {
        return accountRepository.existsById( accountId );
    }

    @Override
    public List<Account> list() {
        return accountRepository.findAll().stream().map( accountMapper::map ).toList();
    }

}
