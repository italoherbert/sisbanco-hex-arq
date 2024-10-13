package italo.sisbanco.core.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.exception.BusinessException;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.UserServicePort;

public class UserServiceImpl implements UserService {
    
    private UserServicePort userServicePort;

    public UserServiceImpl( UserServicePort port ) {
        this.userServicePort = port;
    }

    @Override
    public void prepareUserForCreate(User user) {
        if ( userServicePort.findByUsername( user.getUsername() ).isPresent() )
            throw new BusinessException( BusinessException.USERNAME_EXISTS );        
        if ( userServicePort.findByEmail( user.getEmail() ).isPresent() )
            throw new BusinessException( BusinessException.EMAIL_EXISTS );

        user.setPassword( userServicePort.encodePassword( user.getPassword() ) );        
        user.setActive( true );
    }

    @Override
    public void prepareUserForUpdate(User user) {
        UUID userId = user.getId();
        String username = user.getUsername();
        String email = user.getEmail();

        if ( !userServicePort.exists( userId ) ) 
            throw new BusinessException( BusinessException.USER_NOT_FOUND );        

        Optional<User> usernameUserOp = userServicePort.findByUsername( username );
        if ( usernameUserOp.isPresent() ) {
            UUID usernameUserId = usernameUserOp.get().getId();
            if ( userId != usernameUserId )
                throw new BusinessException( BusinessException.USERNAME_EXISTS );
        }
            
        Optional<User> emailUserOp = userServicePort.findByEmail( email );
        if ( emailUserOp.isPresent() ) {
            UUID emailUserId = emailUserOp.get().getId();
            if ( userId != emailUserId )
                throw new BusinessException( BusinessException.EMAIL_EXISTS );
        }
    }

    @Override
    public List<User> list() {
        return userServicePort.findAll();
    }

}
