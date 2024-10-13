package italo.sisbanco.core.ports.in;

import java.util.List;

import italo.sisbanco.core.domain.User;

public interface UserService {
    
    void prepareUserForCreate( User user );

    void prepareUserForUpdate( User user );

    List<User> list();
    
}
