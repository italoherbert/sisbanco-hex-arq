package italo.sisbanco.core.exception;

public class ErrorException extends RuntimeException {
    
    private String errorMSG;
    private String[] errorParams;

    public ErrorException( String errorMSG, String... errorParams ) {
        this.errorMSG = errorMSG;
        this.errorParams = errorParams;        
    }

    public String error() {
        String error = errorMSG;
        for( int i = 0; i < errorParams.length; i++ )
            error = error.replace( "${"+(i+1)+"}", errorParams[ i ] );
        return error;
    }

    public String getErrorMessage() {
        return errorMSG;
    }

    public String[] getErrorParams() {
        return errorParams;
    }

}
