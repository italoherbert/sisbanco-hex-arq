package italo.sisbanco.core.serviceimpl.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
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

public class AlterUserPasswordImplTests {
    
    @Test
    @DisplayName("Deve alterar password do usuário com sucesso.")
    void shouldAlterPasswordWithSuccess() {
        UserServicePort userServicePort = mock( UserServicePort.class );
        UserService userService = new UserServiceImpl( userServicePort );

        User user = UserMocks.createUser();
        String newPassword = "&Hg98Jsj04";

        when( userServicePort.get( user.getId() ) ).thenReturn( Optional.of( user ) );
        doNothing().when( userServicePort ).update( user );

        userService.alterPassword( user.getId(), newPassword );

        assertEquals( user.getPassword(), newPassword );
    }

    @Test
    @DisplayName("Deve tratar exceção de usuário não existe.")
    void shouldThrowsUserNotFoundTest() {
        UserServicePort userServicePort = mock( UserServicePort.class );
        UserService userService = new UserServiceImpl( userServicePort );

        User user = UserMocks.createUser();
        String newPassword = "&Hg98Jsj04";

        when( userServicePort.get( user.getId() ) ).thenReturn( Optional.empty() );

        BusinessException ex = assertThrows( BusinessException.class, 
            () -> userService.alterPassword( user.getId(), newPassword ) );

        assertNotNull( ex );
        assertEquals( ex.error(), "Usuário não encontrado." );
    }

}
