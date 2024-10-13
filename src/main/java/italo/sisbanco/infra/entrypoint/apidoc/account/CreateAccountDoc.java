package italo.sisbanco.infra.entrypoint.apidoc.account;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import italo.sisbanco.core.domain.Account;
import italo.sisbanco.infra.entrypoint.apidoc.APIDocConstants;
import italo.sisbanco.infra.entrypoint.dto.ErrorResponse;

@Operation(
	summary = "Responsável pelo registro de uma conta no sistema." )
@ApiResponses(value= {
	@ApiResponse( 		
		responseCode = "201",
		description = "Conta registrada no sistema.",
        content=@Content(					
			mediaType = "application/json", 
			schema = @Schema(implementation = Account.class)) ),				
	@ApiResponse(
		responseCode = "400",
		description = APIDocConstants.MSG_400,
		content=@Content(					
			mediaType = "application/json", 
			schema = @Schema(implementation = ErrorResponse.class)))
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateAccountDoc {
    
}
