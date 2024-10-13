package italo.sisbanco.infra.entrypoint.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SaveUserRequest {
    
    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String password;

}
