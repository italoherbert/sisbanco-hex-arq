package italo.sisbanco.infra.entrypoint.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.infra.entrypoint.apidoc.user.ListUsersDoc;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping( "/api/sisbanco/v1/users" )
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @ListUsersDoc
    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.list();
        return ResponseEntity.ok( users );
    }

}
