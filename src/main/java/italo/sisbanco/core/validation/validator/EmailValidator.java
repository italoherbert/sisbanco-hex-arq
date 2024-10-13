package italo.sisbanco.core.validation.validator;

import italo.sisbanco.core.exception.DomainException;
import italo.sisbanco.core.validation.Validator;


public class EmailValidator implements Validator {

    private String fieldValue;

    public EmailValidator( String fieldValue ) {
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() {
        if ( fieldValue == null )
            throw new DomainException( DomainException.INVALID_EMAIL );

        if ( !fieldValue.matches( "\\w+\\.{0,1}\\w+\\@{1}\\w+\\.{1}\\w+" ) )
            throw new DomainException( DomainException.INVALID_EMAIL );                     
    }
    
}
