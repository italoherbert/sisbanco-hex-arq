package italo.sisbanco.infra.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.ports.out.UserServicePort;
import italo.sisbanco.infra.mapper.UserMapper;
import italo.sisbanco.infra.persistence.entity.UserEntity;
import italo.sisbanco.infra.persistence.repository.UserRepository;
import italo.sisbanco.infra.util.PasswordEncoder2;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder2 passwordEncoder;

    @Override
    public void update(User user) {
        UserEntity entity = userMapper.map( user );
        userRepository.save( entity );        
    }

    @Override
    public Optional<User> get(UUID userId) {
        return userRepository.findById( userId ).map( userMapper::map ); 
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername( username ).map( userMapper::map );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail( email ).map( userMapper::map );
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode( password );
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map( userMapper::map ).toList();
    }

    @Override
    public boolean exists(UUID userId) {
        return userRepository.existsById( userId );
    }

}
