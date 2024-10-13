package italo.sisbanco.infra.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.sisbanco.infra.persistence.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    
    @Query("select a from AccountEntity a join UserEntity u on a.user.id=u.id where u.username=?1")
    Optional<AccountEntity> findByUsername( String username );

    @Query("select a from AccountEntity a join UserEntity u on a.user.id=u.id where u.email=?1")
    Optional<AccountEntity> findByEmail( String email );

}
