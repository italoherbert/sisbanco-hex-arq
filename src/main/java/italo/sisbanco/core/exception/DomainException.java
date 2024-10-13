package italo.sisbanco.core.exception;

import italo.sisbanco.error.ErrorException;

public class DomainException extends ErrorException {

    public final static String FIELD_REQUIRED = "O campo '${1}' é de preenchimento obrigatório.";
    public final static String INVALID_EMAIL = "O email informado está em formato inválido.";
    public final static String INVALID_NUMBER_VALUE = "O valor está em formato inválido.";
    public final static String LESS_THAN_ZERO_NUMBER = "O número informado é negativo.";

    public DomainException(String errorMSG, String... params) {
        super(errorMSG, params);
    }
    
}
