package italo.sisbanco.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import italo.sisbanco.core.exception.DomainException;
import italo.sisbanco.mocks.UserMocks;

public class UserTests {
    
    @Test
    @DisplayName( "Deve validar com sucesso" )
    void shouldValidateWithSuccess() {
        User user = UserMocks.createUser();
        user.validate();        
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName( "Deve lançar erro de firstname requerido" )
    void shouldThrowAErrorOfRequiredFirstname( String firstname ) {
        User user = UserMocks.createUser();
        user.setFirstname( firstname ); 
        DomainException ex = assertThrows( DomainException.class, user::validate );

        assertNotNull( ex );
        assertEquals( ex.error(), "O campo 'primeiro nome' é de preenchimento obrigatório." );
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName( "Deve lançar erro de username requerido" )
    void shouldThrowAErrorOfRequiredUsername( String username ) {
        User user = UserMocks.createUser();
        user.setUsername( username ); 
        DomainException ex = assertThrows( DomainException.class, user::validate );

        assertNotNull( ex );
        assertEquals( ex.error(), "O campo 'nome de usuário' é de preenchimento obrigatório." );
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName( "Deve lançar erro de password requerido" )
    void shouldThrowAErrorOfRequiredPassword( String password ) {
        User user = UserMocks.createUser();
        user.setPassword( password ); 
        DomainException ex = assertThrows( DomainException.class, user::validate );

        assertNotNull( ex );
        assertEquals( ex.error(), "O campo 'senha' é de preenchimento obrigatório." );
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName( "Deve lançar erro de email requerido" )
    void shouldThrowAErrorOfRequiredEmail( String email ) {
        User user = UserMocks.createUser();
        user.setEmail( email ); 
        DomainException ex = assertThrows( DomainException.class, user::validate );

        assertNotNull( ex );
        assertEquals( ex.error(), "O campo 'email' é de preenchimento obrigatório." );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ma.@outlook.com", ".ia@outlook.com", "@outlook.com", 
        "maria.carla@.com", "maria.carla@out.", "maria.carla@.com",
        "maria.carla@out$ook.com", "maria.carla@outlook.c#m"
    })
    @DisplayName( "Deve lançar erro de email requerido" )
    void shouldThrowAErrorOfEmailInvalido( String email ) {
        User user = UserMocks.createUser();
        user.setEmail( email ); 

        DomainException ex = assertThrows( DomainException.class, user::validate );

        assertNotNull( ex );
        assertEquals( ex.error(), "O email informado está em formato inválido." );
    }

}
