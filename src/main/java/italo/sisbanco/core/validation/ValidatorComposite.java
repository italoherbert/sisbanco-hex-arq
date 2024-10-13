package italo.sisbanco.core.validation;

import java.util.List;

public class ValidatorComposite implements Validator {

    private final List<Validator> validators;

    public ValidatorComposite( List<Validator> validators ) {
        this.validators = validators;
    }

    public void validate() {
        for( Validator v : validators )
            v.validate();
    }

}
