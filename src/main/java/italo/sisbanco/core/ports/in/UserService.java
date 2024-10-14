package italo.sisbanco.core.ports.in;

import java.util.List;
import java.util.UUID;

import italo.sisbanco.core.domain.User;

public interface UserService {
    
    void prepareUserForCreate( User user );

    void prepareUserForUpdate( User user );

    void alterPassword( UUID userId, String newPassword );
    
    List<User> list();

}
