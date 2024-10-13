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
public class Account implements Domain {
    
    private UUID id;

    private double balance;
    
    private User user;

    @Override
    public void validate() {
        user.validate();

        List<Validator> validators = new ArrayList<>();
        validators.addAll( 
            ValidationBuilder.of( "balanço", balance )
                .build() 
        );

        new ValidatorComposite( validators ).validate();
    }

}
