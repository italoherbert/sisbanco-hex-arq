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
import italo.sisbanco.infra.entrypoint.apidoc.APIDocConstants;
import italo.sisbanco.infra.entrypoint.dto.ErrorResponse;
import italo.sisbanco.infra.entrypoint.dto.account.BalanceResponse;

@Operation(
	summary = "Responsável por realizar o depósito de valor em uma conta." )
@ApiResponses(value= {
	@ApiResponse( 		
		responseCode = "200",
		description = "Valor depositado com sucesso.",
		content=@Content(
			mediaType = "application/json",
			schema = @Schema(implementation = BalanceResponse.class)
		) ),				
	@ApiResponse(
		responseCode = "400",
		description = APIDocConstants.MSG_400,
		content=@Content(					
			mediaType = "application/json", 
			schema = @Schema(implementation = ErrorResponse.class)))
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DepositDoc {
    
}
