package italo.sisbanco.core.serviceimpl.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.exception.BusinessException;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.UserServicePort;
import italo.sisbanco.core.serviceimpl.UserServiceImpl;
import italo.sisbanco.mocks.UserMocks;

public class PrepareUserForUpdateServiceImplTests {
    
    @Test
    @DisplayName("Deve preparar com sucesso o usuário para update.")
    void shouldPrepareUserForUpdateWithSuccess() {
        String encodedPassword = "10029837646784774";

        User user = UserMocks.createUser();      

        UserServicePort userServicePort = mock( UserServicePort.class );
        UserService userService = new UserServiceImpl( userServicePort );

        when( userServicePort.exists( user.getId() ) ).thenReturn( true );
        when( userServicePort.findByUsername( user.getUsername() ) ).thenReturn( Optional.empty() );
        when( userServicePort.findByEmail( user.getEmail() ) ).thenReturn( Optional.empty() );
        when( userServicePort.encodePassword( user.getPassword() ) ).thenReturn( encodedPassword );

        userService.prepareUserForUpdate( user ); 

        when( userServicePort.findByUsername( user.getUsername() ) ).thenReturn( Optional.of( user ) );
        userService.prepareUserForUpdate( user ); 

        when( userServicePort.findByEmail( user.getEmail() ) ).thenReturn( Optional.of( user ) );
        userService.prepareUserForUpdate( user ); 
    }

    @Test
    @DisplayName("Deve tratar exceção de username já existe na preparação de update do usuário")
    void shouldThrowsUsernameExistsForPrepareUpdate() {
        String encodedPassword = "10029837646784774";

        User user = UserMocks.createUser();      

        UserServicePort userServicePort = mock( UserServicePort.class );
        UserService userService = new UserServiceImpl( userServicePort );

        User other = UserMocks.createUser();

        when( userServicePort.exists( user.getId() ) ).thenReturn( true );
        when( userServicePort.findByUsername( user.getUsername() ) ).thenReturn( Optional.of( other ) );
        when( userServicePort.findByEmail( user.getEmail() ) ).thenReturn( Optional.empty() );
        when( userServicePort.encodePassword( user.getPassword() ) ).thenReturn( encodedPassword );

        BusinessException ex = assertThrows( BusinessException.class, () -> userService.prepareUserForUpdate( user ) );
        assertNotNull( ex );
        assertEquals( ex.error(), "O nome de usuário informado já existe registrado.");
    }

    @Test
    @DisplayName("Deve tratar exceção de username já existe na preparação de update do usuário")
    void shouldThrowsEmailExistsForPrepareUpdate() {
        String encodedPassword = "10029837646784774";

        User user = UserMocks.createUser();      

        UserServicePort userServicePort = mock( UserServicePort.class );
        UserService userService = new UserServiceImpl( userServicePort );

        User other = UserMocks.createUser();

        when( userServicePort.exists( user.getId() ) ).thenReturn( true );
        when( userServicePort.findByUsername( user.getUsername() ) ).thenReturn( Optional.empty() );
        when( userServicePort.findByEmail( user.getEmail() ) ).thenReturn( Optional.of( other ) );
        when( userServicePort.encodePassword( user.getPassword() ) ).thenReturn( encodedPassword );

        BusinessException ex = assertThrows( BusinessException.class, () -> userService.prepareUserForUpdate( user ) );
        assertNotNull( ex );
        assertEquals( ex.error(), "O email informado já existe registrado.");
    }

}
