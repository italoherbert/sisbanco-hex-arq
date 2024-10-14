package italo.sisbanco.infra.entrypoint.dto.account;

import java.io.Serializable;

import italo.sisbanco.infra.entrypoint.dto.user.CreateUserRequest;
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
public class CreateAccountRequest implements Serializable {
    
    private CreateUserRequest user;

}
