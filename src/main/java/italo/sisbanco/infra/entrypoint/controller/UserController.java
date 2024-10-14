package italo.sisbanco.infra.entrypoint.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import italo.sisbanco.core.domain.User;
import italo.sisbanco.core.ports.in.UserService;
import italo.sisbanco.infra.entrypoint.apidoc.user.AlterPasswordUserDoc;
import italo.sisbanco.infra.entrypoint.apidoc.user.ListUsersDoc;
import italo.sisbanco.infra.entrypoint.dto.user.SavePasswordUserRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping( "/api/sisbanco/v1/users" )
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @AlterPasswordUserDoc
    @PatchMapping("/{userId}/alter-password")
    public ResponseEntity<Object> alterPassword(
            @PathVariable UUID userId,
            @RequestBody SavePasswordUserRequest request ) {
        
        String newPassword = request.getPassword();
        userService.alterPassword( userId, newPassword );
        return ResponseEntity.ok().build();
    }

    @ListUsersDoc
    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.list();
        return ResponseEntity.ok( users );
    }

}
