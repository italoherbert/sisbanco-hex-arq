package italo.sisbanco.infra.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.sisbanco.infra.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    
    @Query( "select u from UserEntity u where u.username=?1" )
    Optional<UserEntity> findByUsername( String username );

    @Query( "select u from UserEntity u where u.email=?1" )
    Optional<UserEntity> findByEmail( String email );

}
