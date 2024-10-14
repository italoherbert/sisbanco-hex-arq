package italo.sisbanco.infra.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.infra.entrypoint.dto.user.SaveUserRequest;
import italo.sisbanco.infra.persistence.entity.UserEntity;
import italo.sisbanco.mocks.UserMocks;

@SpringBootTest
public class UserMapperTests {
 
    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("Deve retornar um UserEntity a partir de um User")
    void shoudMapUserToUserEntity() {
        User user = UserMocks.createUser();

        UserEntity entity = userMapper.map( user );

        assertEquals( user.getFirstname(), entity.getFirstname() );
        assertEquals( user.getLastname(), entity.getLastname() );
        assertEquals( user.getEmail(), entity.getEmail() );
        assertEquals( user.getUsername(), entity.getUsername() );
        assertEquals( user.getPassword(), entity.getPassword() );
        assertEquals( user.isActive(), entity.isActive() ); 
    }

    @Test
    @DisplayName("Deve retornar um User a partir de um UserEntity")
    void shoudMapUserEntityToUser() {
        UserEntity entity = UserMocks.createUserEntity();

        User user = userMapper.map( entity );

        assertEquals( user.getId(), entity.getId() );
        assertEquals( user.getFirstname(), entity.getFirstname() );
        assertEquals( user.getLastname(), entity.getLastname() );
        assertEquals( user.getEmail(), entity.getEmail() );
        assertEquals( user.getUsername(), entity.getUsername() );
        assertEquals( user.getPassword(), entity.getPassword() );
        assertEquals( user.isActive(), entity.isActive() ); 
    }

    @Test
    @DisplayName("Deve retornar um User a partir de um CreateUserRequest")
    void shoudMapCreateUserRequestEntityToUser() {
        SaveUserRequest request = UserMocks.createSaveUserRequest();

        User user = userMapper.map( request );

        assertEquals( user.getFirstname(), request.getFirstname() );
        assertEquals( user.getLastname(), request.getLastname() );
        assertEquals( user.getEmail(), request.getEmail() );
        assertEquals( user.getUsername(), request.getUsername() );
    }

}
