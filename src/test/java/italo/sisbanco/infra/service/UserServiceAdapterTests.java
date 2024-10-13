package italo.sisbanco.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.infra.mapper.UserMapper;
import italo.sisbanco.infra.persistence.entity.UserEntity;
import italo.sisbanco.infra.persistence.repository.UserRepository;
import italo.sisbanco.infra.util.PasswordEncoder2;
import italo.sisbanco.mocks.UserMocks;

@SpringBootTest
public class UserServiceAdapterTests {
    
    @Autowired
    private UserServiceAdapter userServiceAdapter;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder2 passwordEncoder;

    @Test
    @DisplayName("Deve retornar um User pelo ID com sucesso")
    void getByIdSuccessTest() {
        UserEntity userEntity = UserMocks.createUserEntity();        
        User user = userMapper.map( userEntity );
        
        when( userRepository.findById( userEntity.getId() ) ).thenReturn( Optional.of( userEntity ) );

        Optional<User> userOp = userServiceAdapter.get( user.getId() );        

        assertTrue( userOp.isPresent() );
        assertEquals( user.getId(), userOp.get().getId() );
    }

    @Test
    @DisplayName("Retorna verdade porque o id do usuário existe.")
    void existsTest() {
        UserEntity entity = UserMocks.createUserEntity();
        User user = userMapper.map( entity );

        when( userRepository.existsById( user.getId() ) ).thenReturn( true );

        boolean exists = userServiceAdapter.exists( user.getId() );
        assertTrue( exists );
    }

    @Test
    @DisplayName("Retorna falso porque o id do usuário não existe.")
    void notExistsTest() {
        UserEntity entity = UserMocks.createUserEntity();
        User user = userMapper.map( entity );        

        when( userRepository.existsById( user.getId() ) ).thenReturn( false );
        
        boolean exists = userServiceAdapter.exists( user.getId() );
        assertFalse( exists );
    }

    @Test
    @DisplayName("Deve retornar a lista de todos os usuários")
    void findAllUsersTest() {
        List<User> users = UserMocks.createUserList( 5 );
        List<UserEntity> entities = users.stream().map( u -> userMapper.map( u ) ).toList();

        when( userRepository.findAll() ).thenReturn( entities );

        List<User> usersResp = userServiceAdapter.findAll();

        assertNotNull( usersResp );
        assertEquals( usersResp, users );
    }


    @Test
    @DisplayName("Procura User por username com sucesso")
    void findByUsernameSuccessTest() {
        UserEntity entity = UserMocks.createUserEntity();
        User user = userMapper.map( entity );

        when( userRepository.findByUsername( user.getUsername() ) ).thenReturn( Optional.of( entity ) );

        Optional<User> userOp = userServiceAdapter.findByUsername( user.getUsername() );

        assertTrue( userOp.isPresent() );
        assertEquals( user.getId(), userOp.get().getId() );
        assertEquals( user.getUsername(), userOp.get().getUsername() );
        verify( userRepository, times( 1 ) ).findByUsername( user.getUsername() );
    }

    @Test
    @DisplayName("Procura User por email com sucesso")
    void findByEmailSuccessTest() {
        UserEntity entity = UserMocks.createUserEntity();
        User user = userMapper.map( entity );

        when( userRepository.findByEmail( user.getEmail() ) ).thenReturn( Optional.of( entity ) );

        Optional<User> userOp = userServiceAdapter.findByEmail( user.getEmail() );

        assertTrue( userOp.isPresent() );
        assertEquals( user.getId(), userOp.get().getId() );
        assertEquals( user.getEmail(), userOp.get().getEmail() );
        verify( userRepository, times( 1 ) ).findByEmail( user.getEmail() );
    }

    @Test
    @DisplayName("Deve codificar o password com sucesso.")
    void encodePasswordTest() {
        String password = "abc";

        String passwordEncoded = passwordEncoder.encode( password ); 

        String passwordEncoded2 = userServiceAdapter.encodePassword( password );

        assertEquals( passwordEncoded, passwordEncoded2 ); 
    }   

}
