package italo.sisbanco.core.validation.validator;

import italo.sisbanco.core.exception.DomainException;
import italo.sisbanco.core.validation.Validator;

public class RequiredValidator implements Validator {

    private String fieldName;
    private String fieldValue;

    public RequiredValidator(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() throws DomainException {
        if ( fieldValue == null )
            throw new DomainException( DomainException.FIELD_REQUIRED, fieldName );
        if ( fieldValue.isBlank() )
            throw new DomainException( DomainException.FIELD_REQUIRED, fieldName );
    }
    
}
