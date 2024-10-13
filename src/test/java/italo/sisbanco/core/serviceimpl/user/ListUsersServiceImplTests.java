package italo.sisbanco.core.serviceimpl.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.core.ports.out.UserServicePort;
import italo.sisbanco.core.serviceimpl.UserServiceImpl;
import italo.sisbanco.mocks.UserMocks;

public class ListUsersServiceImplTests {
    
    @Test
    @DisplayName("Deve retornar a lista de Users")
    void shouldReturnTheUsersListTest() {
        List<User> users = UserMocks.createUserList( 5 );

        UserServicePort userServicePort = mock( UserServicePort.class );
        UserService userService = new UserServiceImpl( userServicePort );

        when( userServicePort.findAll() ).thenReturn( users );

        List<User> users2 = userService.list();

        assertEquals( users, users2 ); 
    }

}
