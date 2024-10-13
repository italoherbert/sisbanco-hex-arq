package italo.sisbanco.core.validation.validator;

import italo.sisbanco.core.exception.DomainException;
import italo.sisbanco.core.validation.Validator;

public class NotNegativeValidator implements Validator {

    private final String fieldValue;

    public NotNegativeValidator( String fieldValue ) {
        this.fieldValue = fieldValue;
    }

    @Override
    public void validate() {
        try {
            double value = Double.parseDouble( fieldValue );
            if ( value < 0 )    
                throw new DomainException( DomainException.LESS_THAN_ZERO_NUMBER );
                
        } catch ( NumberFormatException e ) {
            throw new DomainException( DomainException.INVALID_NUMBER_VALUE );
        }        
    }
    
}
