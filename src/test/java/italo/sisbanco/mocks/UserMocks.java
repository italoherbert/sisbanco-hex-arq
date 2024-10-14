package italo.sisbanco.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.javafaker.Faker;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.infra.entrypoint.dto.user.SaveUserRequest;
import italo.sisbanco.infra.persistence.entity.UserEntity;

public class UserMocks {
    
    private final static Faker faker = new Faker();

    private static String securePassword = "a.%$_a17U$pe1O.*7";

    public static User createUser() {
        return User.builder()
            .id( UUID.randomUUID() ) 
            .firstname( faker.name().firstName() )
            .lastname( faker.name().lastName() )
            .username( faker.name().username() )
            .password( securePassword )
            .email( faker.internet().emailAddress() ) 
            .active( true ) 
            .build();
    }

    public static SaveUserRequest createSaveUserRequest() {
        return SaveUserRequest.builder()
            .firstname( faker.name().firstName() )
            .lastname( faker.name().lastName() )
            .username( faker.name().username() )
            .email( faker.internet().emailAddress() ) 
            .build();
    }

    public static UserEntity createUserEntity() {
        return UserEntity.builder()
            .id( UUID.randomUUID() ) 
            .firstname( faker.name().firstName() )
            .lastname( faker.name().lastName() )
            .username( faker.name().username() )
            .password( securePassword )
            .email( faker.internet().emailAddress() ) 
            .active( true ) 
            .build();
    }

    public static List<User> createUserList( int size ) {
        List<User> list = new ArrayList<>( size );
        for( int i = 0; i < size; i++ )
            list.add( createUser() );
        return list;
    }

}
