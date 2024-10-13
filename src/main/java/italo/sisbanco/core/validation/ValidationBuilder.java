package italo.sisbanco.core.validation;

import java.util.ArrayList;
import java.util.List;

import italo.sisbanco.core.validation.validator.EmailValidator;
import italo.sisbanco.core.validation.validator.NotNegativeValidator;
import italo.sisbanco.core.validation.validator.RequiredValidator;

public class ValidationBuilder {
    
    private List<Validator> validators = new ArrayList<>();
    private String fieldName;
    private String fieldValue;
    
    public ValidationBuilder( String fieldName, String fieldValue ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public static ValidationBuilder of( String fieldName, String fieldValue ) {
        return new ValidationBuilder( fieldName, fieldValue );
    }

    public static ValidationBuilder of( String fieldName, Object fieldValue ) {
        return new ValidationBuilder( fieldName, fieldValue.toString() );
    }

    public ValidationBuilder required() {
        validators.add( new RequiredValidator( fieldName, fieldValue ) );
        return this;
    }

    public ValidationBuilder email() {
        validators.add( new EmailValidator( fieldValue ) );
        return this;
    }

    public ValidationBuilder notNegativeNumber() {
        validators.add( new NotNegativeValidator( fieldValue ) );
        return this;
    }

    public List<Validator> build() {
        return validators;
    }

}
