package italo.sisbanco.infra.mapper;

import org.springframework.stereotype.Component;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.infra.entrypoint.dto.user.SaveUserRequest;
import italo.sisbanco.infra.persistence.entity.UserEntity;

@Component
public class UserMapper {
    
    public User map( UserEntity entity ) {
        return User.builder()
            .id( entity.getId() )
            .firstname( entity.getFirstname() )
            .lastname( entity.getLastname() )
            .email( entity.getEmail() )
            .username( entity.getUsername() )
            .password( entity.getPassword() )
            .active( entity.isActive() )
            .build();
    }

    public UserEntity map( User user ) {
        UserEntity entity =  UserEntity.builder()
            .id( user.getId() )
            .firstname( user.getFirstname() )
            .lastname( user.getLastname() )
            .email( user.getEmail() )
            .username( user.getUsername() )
            .password( user.getPassword() )
            .active( user.isActive() )
            .build();
        
        return entity;
    }

    public User map( SaveUserRequest request ) {
        return User.builder()
            .firstname( request.getFirstname() )
            .lastname( request.getLastname() )
            .email( request.getEmail() )
            .username( request.getUsername() )
            .build();
    }

}
