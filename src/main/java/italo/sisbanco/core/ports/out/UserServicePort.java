package italo.sisbanco.core.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import italo.sisbanco.core.domain.User;

public interface UserServicePort {
    
    void update( User user );

    List<User> findAll();

    Optional<User> get( UUID userId );

    boolean exists( UUID userId );

    Optional<User> findByUsername( String username );

    Optional<User> findByEmail( String email );

    String encodePassword( String password );

}
