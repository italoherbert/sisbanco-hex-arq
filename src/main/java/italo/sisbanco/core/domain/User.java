package italo.sisbanco.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import italo.sisbanco.core.validation.ValidationBuilder;
import italo.sisbanco.core.validation.Validator;
import italo.sisbanco.core.validation.ValidatorComposite;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User implements Domain {
    
    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String password;

    private boolean active;

    @Override
    public void validate() {
        List<Validator> validators = new ArrayList<>();
        validators.addAll( 
            ValidationBuilder.of( "primeiro nome", firstname )
                .required()                
                .build()
        );
        validators.addAll( 
            ValidationBuilder.of( "nome de usuário", username )
                .required()                
                .build()
        );
        validators.addAll( 
            ValidationBuilder.of( "senha", password )
                .required()                
                .build()
        );
        validators.addAll( 
            ValidationBuilder.of( "email", email )
                .required()     
                .email()           
                .build()
        );
        
        new ValidatorComposite( validators ).validate();
    }

}
