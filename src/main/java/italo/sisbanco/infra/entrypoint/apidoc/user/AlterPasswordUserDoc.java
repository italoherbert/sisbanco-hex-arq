package italo.sisbanco.infra.entrypoint.apidoc.user;

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

@Operation(
	summary = "Responsável pela alteração de password do usuário." )
@ApiResponses(value= {
	@ApiResponse( 		
		responseCode = "200",
		description = "Password alterado com sucesso." ),				
	@ApiResponse(
		responseCode = "400",
		description = APIDocConstants.MSG_400,
		content=@Content(					
			mediaType = "application/json", 
			schema = @Schema(implementation = ErrorResponse.class)))
})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlterPasswordUserDoc {
    
}
